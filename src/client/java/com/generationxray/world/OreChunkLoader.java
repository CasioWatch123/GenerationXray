package com.generationxray.world;

import com.casiowatch123.vchunklib.generation.virtual.world.VWorldService;
import com.casiowatch123.vchunklib.generation.virtual.world.chunk.VChunkGenerationContext;
import com.casiowatch123.vchunklib.generation.virtual.world.chunk.VChunkGenerationStep;
import com.casiowatch123.vchunklib.generation.virtual.world.chunk.VChunkGenerationSteps;
import com.generationxray.GenerationXrayClient;
import com.generationxray.ore.OreType;
import com.generationxray.world.gen.*;
import it.unimi.dsi.fastutil.longs.*;
import net.minecraft.block.BlockState;
import net.minecraft.util.collection.BoundedRegionArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.*;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class OreChunkLoader {
    private static final Logger LOGGER = GenerationXrayClient.LOGGER;
    
    private static final int MAX_DEPENDENT_REGION_RADIUS = 10;
    private static final ChunkStatus START_STATUS = ChunkStatus.EMPTY;
    private static final ChunkStatus FINAL_STATUS = ChunkStatus.FEATURES;
    private static final List<Integer> GENERATE_RADIUS_LIST = List.of(10, 10, 2, 2, 1, 1, 1, 0);
    private static final List<ChunkStatus> CHUNK_STATUS_LIST = ChunkStatus.createOrderedList()
            .subList(START_STATUS.getIndex(), FINAL_STATUS.getIndex() + 1);
    
    private static final ReentrantLock lock = new ReentrantLock();
    
    private static volatile PutConcurrentOreDataMap globalOreDataStorage;
    
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(4);
    
    private BoundedRegionArray<GenChunkHolder> cache;
    
    private final VWorldService world;
    private final VChunkGenerationContext generationContext;
    private final PalettesFactory palettesFactory;
    
    public OreChunkLoader(VWorldService world, PalettesFactory palettesFactory){
        this.world = world;
        this.generationContext = world.getGenerationContext();
        this.palettesFactory = palettesFactory;
    }

    public CompletableFuture<BoundedRegionArray<GenChunkHolder>> loadChunks(ChunkPos centerPos, int centerRadius) {
        if (centerRadius < 0) {
            throw new IllegalArgumentException("center radius is wrong: " + centerRadius);
        }

        int radius = centerRadius + MAX_DEPENDENT_REGION_RADIUS;
        int size = 2*radius + 1;

        try {
            return CompletableFuture.supplyAsync(() -> {
                        BoundedRegionArray<GenChunkHolder> genChunkHolderArray = generateRegion(centerPos, radius, this.cache);
                        BoundedRegionArray<Chunk> chunks = BoundedRegionArray.create(
                                centerPos.x, centerPos.z, radius,
                                (x, z) -> genChunkHolderArray.get(x, z).getChunk());
                        
                        PutConcurrentOreDataMap storage;
                        if (this.world.worldContext().getDimensionKey() == World.OVERWORLD) {
                            storage = new OverworldPutConcurrentOreDataMap();
                        } else {
                            storage = new NetherPutConcurrentOreDataMap();
                        }

                        for (ChunkStatus status : CHUNK_STATUS_LIST) {
                            int currentGeneratingSize = 2 * (GENERATE_RADIUS_LIST.get(status.getIndex()) + centerRadius) + 1;
                            int blank = (size - currentGeneratingSize) / 2;

                            VChunkGenerationStep step = VChunkGenerationSteps.GENERATION.get(status);

                            boolean lockFlag = false;
                            if (status == ChunkStatus.FEATURES || status == ChunkStatus.NOISE) {
                                lock.lock();
                                lockFlag = true;
                                globalOreDataStorage = storage;
                            }
                            try {
                                int total = (size - 2 * blank) * (size - 2 * blank);
                                if (total <= 0) {
                                    continue;
                                }
                                AtomicInteger remaining = new AtomicInteger(total);
                                CompletableFuture<Void> done = new CompletableFuture<>();

                                for (int j = blank; j < size - blank; j++) {
                                    for (int k = blank; k < size - blank; k++) {
                                        int x = centerPos.x - radius + k;
                                        int z = centerPos.z - radius + j;
                                        generate(genChunkHolderArray.get(x, z), step, chunks, genChunkHolderArray)
                                                .whenComplete((chunk, ex) -> {
                                                    if (ex != null) {
                                                        done.completeExceptionally(ex);
                                                    }
                                                    if (remaining.decrementAndGet() == 0) {
                                                        done.complete(null);
                                                    }
                                                });
                                    }
                                }

                                done.join();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            } finally {
                                if (lockFlag) {
                                    globalOreDataStorage = null;
                                    lock.unlock();
                                }
                            }
                        }

                        Map<OreType, LongCollection> map = storage.getDataMap();

                        map.keySet().forEach(type -> {
                            Long2ObjectMap<LongList> map1 = new Long2ObjectArrayMap<>();
                            for (long pos : map.get(type)) {
                                int x = BlockPos.unpackLongX(pos);
                                int z = BlockPos.unpackLongZ(pos);
                                int chunkX = x >> 4;
                                int chunkZ = z >> 4;

                                long chunkPos = ChunkPos.toLong(chunkX, chunkZ);
                                if (!map1.containsKey(chunkPos)) {
                                    map1.put(chunkPos, new LongArrayList());
                                }
                                map1.get(chunkPos).add(pos);
                            }
                            for (long chunkPos : map1.keySet()) {
                                int x = ChunkPos.getPackedX(chunkPos);
                                int y = ChunkPos.getPackedZ(chunkPos);
                                genChunkHolderArray.get(x, y).registerOre(map1.get(chunkPos), type);
                            }
                        });

                        this.cache = genChunkHolderArray;

                        return BoundedRegionArray.create(
                                centerPos.x, centerPos.z, centerRadius + 1,
                                genChunkHolderArray::get);
                    }, EXECUTOR)
                    .exceptionally(ex -> {
                        LOGGER.warn("Region generation failed: [{}, {}]", centerPos.x, centerPos.z);
                        return null;
                    });
        } catch (RejectedExecutionException e) {
            return CompletableFuture.completedFuture(
                    generateRegion(centerPos, centerRadius + 1, null));
        }
    }

    private BoundedRegionArray<GenChunkHolder> generateRegion(
            ChunkPos centerPos, int radius, 
            BoundedRegionArray<GenChunkHolder> chunkHolderCache) {
        int size = 2*radius + 1;
        int startX = centerPos.x - radius;
        int startZ = centerPos.z - radius;

        Long2ObjectMap<GenChunkHolder> chunks = new Long2ObjectOpenHashMap<>();
        
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                int xPos = i + startX;
                int zPos = j + startZ;
                
                if (chunkHolderCache == null || !chunkHolderCache.isWithinBounds(xPos, zPos)) {
                    ChunkPos pos = new ChunkPos(i + startX, j + startZ);
                    chunks.put(pos.toLong(), new GenChunkHolder(pos, generateProtoChunk(pos)));
                }
            }
        }
        
        return BoundedRegionArray.create(
                centerPos.x, centerPos.z, radius,
                (x, z) -> {
                    if (chunkHolderCache != null && chunkHolderCache.isWithinBounds(x, z)) {
                        return chunkHolderCache.get(x, z);
                    }
                    return chunks.get(ChunkPos.toLong(x, z));
                });
    }
    
    private CompletableFuture<Chunk> generate(
            GenChunkHolder chunkHolder, 
            VChunkGenerationStep step, 
            BoundedRegionArray<Chunk> chunks, 
            BoundedRegionArray<? extends SemaphoreHolder> lockableChunkArray) throws InterruptedException {
        long[] ordinalLocks = lockChunks(chunkHolder.getPos(), step.blockStateWriteRadius(), lockableChunkArray);
        
        if (chunkHolder.getStatus().isEarlierThan(step.targetStatus())) {
            try {
                return step.run(this.generationContext, chunks, chunkHolder.getChunk())
                        .whenComplete((chunk, ex) -> unlockChunks(chunkHolder.getPos(), step.blockStateWriteRadius(), lockableChunkArray, ordinalLocks))
                        .thenApply(chunk -> {
                            chunkHolder.setStatus(step.targetStatus());
                            return chunk;
                        });
            } catch (Throwable ex) {
                unlockChunks(chunkHolder.getPos(), step.blockStateWriteRadius(), lockableChunkArray, ordinalLocks);
                throw ex;
            }
        }

        unlockChunks(chunkHolder.getPos(), step.blockStateWriteRadius(), lockableChunkArray, ordinalLocks);
        return CompletableFuture.completedFuture(chunkHolder.getChunk());
    }
    
    private static long[] lockChunks(ChunkPos centerPos, int radius, BoundedRegionArray<? extends SemaphoreHolder> lockableChunks) throws InterruptedException {
        int startX = centerPos.x - radius;
        int startZ = centerPos.z - radius;
        int size = 2 * radius + 1;
        
        LongList list = new LongArrayList();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                list.add(ChunkPos.toLong(startX + i, startZ + j));
            }
        }
        long[] locks = list.longStream()
                .sorted()
                .toArray();
        
        for(long l : locks) {
            lockableChunks.get(ChunkPos.getPackedX(l), ChunkPos.getPackedZ(l))
                    .getMutex()
                    .acquire();
        }
        return locks;
    }

    private static void unlockChunks(ChunkPos centerPos, int radius, BoundedRegionArray<? extends SemaphoreHolder> lockableChunks, long[] locks) {
        for(long l : LongArrays.reverse(locks)) {
            lockableChunks.get(ChunkPos.getPackedX(l), ChunkPos.getPackedZ(l))
                    .getMutex()
                    .release();
        }
    }

    private ProtoChunk generateProtoChunk(ChunkPos chunkPos) {
        return new ProtoChunk(
                chunkPos, 
                UpgradeData.NO_UPGRADE_DATA, 
                this.world.world(), 
                this.palettesFactory, 
                null);
    }
    
    public static void registerOre(BlockState state, long pos) {
        if (globalOreDataStorage != null) {
            OreType type = OreType.getOreTypeOrNull(state);
            if (type != null) {
                globalOreDataStorage.put(type, pos);
            } else {
                LOGGER.warn("unknown type: {}", state);
            }
        }
    }
    
    public static void shutdown() {
        EXECUTOR.shutdown();
    }
    
    public void clearCache() {
        this.cache = null;
    }
}
