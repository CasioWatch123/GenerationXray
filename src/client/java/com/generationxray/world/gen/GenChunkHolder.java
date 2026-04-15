package com.generationxray.world.gen;

import com.generationxray.ore.OreDataMap;
import com.generationxray.ore.OreType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongCollection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GenChunkHolder implements SemaphoreHolder {
    private final Chunk chunk;
    private final ChunkPos pos;
    private ChunkStatus status;
    
    private final Semaphore mutex = new Semaphore(1, true);

    private final Int2ObjectMap<OreDataMap> oreSectionMap = new Int2ObjectOpenHashMap<>();
    private final Map<OreType, Object> oreMapLock = new EnumMap<>(OreType.class);
    
    private final BlockPos.Mutable mutable = new BlockPos.Mutable();
    public GenChunkHolder(ChunkPos pos, Chunk chunk) {
        this.chunk = chunk;
        this.pos = pos;
        this.status = ChunkStatus.EMPTY;
        for(OreType type : OreType.values()) {
            oreMapLock.put(type, new Object());
        }
    }

    public void registerOre(LongCollection posCollection, OreType type) {
        synchronized (oreMapLock.get(type)) {
            for(long pos : posCollection) {
                mutable.set(pos);
                if (chunk.getBlockState(mutable).isOf(type.block)) {
                    int sectionIdx = mutable.getY() >> 4;

                    if (!oreSectionMap.containsKey(sectionIdx)) {
                        oreSectionMap.put(sectionIdx, new OreDataMap());
                    }
                    oreSectionMap.get(sectionIdx).put(type, pos);
                }
            }
        }
    }

    public LongCollection getOreData(OreType type, int startSectionIdx, int endSectionIdx) {
        LongCollection collection = new LongArrayList();
        
        synchronized (oreMapLock.get(type)) {
            for (int i = startSectionIdx; i <= endSectionIdx; i++) {
                OreDataMap data = oreSectionMap.get(i);
                if (data != null && !data.get(type).isEmpty()) {
                    collection.addAll(data.get(type));
                }
            }
        }

        return collection;
    }

    public LongCollection getOreData(OreType type) {
        LongCollection collection = new LongArrayList();

        synchronized (oreMapLock.get(type)) {
            oreSectionMap.values().forEach(oreDataMap -> {
                collection.addAll(oreDataMap.get(type));
            });
        }

        return collection;
    }
    
    public void setStatus(ChunkStatus status) {
        this.status = status;
    }
    
    public ChunkStatus getStatus() {
        return this.status;
    }
    
    public Chunk getChunk() {
        return this.chunk;
    }
    
    public ChunkPos getPos() {
        return this.pos;
    }
    
    @Override
    public Semaphore getMutex() {
        return mutex;
    }
}
