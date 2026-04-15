package com.generationxray;

import com.casiowatch123.vchunklib.generation.virtual.VUtils;
import com.casiowatch123.vchunklib.generation.virtual.world.VWorldService;
import com.generationxray.config.GenerationXrayConfig;
import com.generationxray.ore.OreType;
import com.generationxray.render.OreRenderOption;
import com.generationxray.world.OreChunkLoader;
import com.generationxray.world.gen.GenChunkHolder;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongCollection;
import me.shedaniel.autoconfig.ConfigHolder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.collection.BoundedRegionArray;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.PalettesFactory;
import net.minecraft.world.dimension.DimensionType;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class GenerationXrayTickService {
    private static final Logger LOGGER = GenerationXray.LOGGER;
    private final ConfigHolder<GenerationXrayConfig> configHolder;
    private PalettesFactory palettesFactory;
    
    private VWorldService vWorld;
    private OreChunkLoader loader;
    private ChunkPos pos;
    private RegistryKey<World> dimensionKey;
    private int loadRadius = -1;
    
    private CompletableFuture<BoundedRegionArray<GenChunkHolder>> regionFuture;
    private LoadStatus regionLoadStatus = LoadStatus.LOAD_DISABLED;
    
    private volatile Map<LongCollection, OreRenderOption> renderData = null;
//    private volatile CompletableFuture<Map<LongCollection, OreRenderOption>> renderDataFuture = null;
    public GenerationXrayTickService(ConfigHolder<GenerationXrayConfig> configHolder) {
        this.configHolder = configHolder;
    }
    public void updateTick(MinecraftClient client) {
        if (GenerationXrayClient.DRM == null) {
            return;
        } else if (this.palettesFactory == null) {
            this.palettesFactory = PalettesFactory.fromRegistryManager(GenerationXrayClient.DRM);
        }
        
        if (client.player == null || client.world == null || getDimensionKey(client.world) == World.END) {
            this.renderData = null;
            return;
        }

        ClientPlayerEntity player = client.player;
        ClientWorld world = client.world;
        
        //loader
        
        if (this.vWorld == null || this.dimensionKey == null
         ||configHolder.getConfig().seed != this.vWorld.worldContext().getSeed() 
         || this.dimensionKey != getDimensionKey(world)) {
            if (getDimensionKey(world) != World.END) {
                
                this.dimensionKey = getDimensionKey(world);
                this.vWorld = new VWorldService(
                        GenerationXrayClient.DRM,
                        VUtils.createDimensionArg(this.dimensionKey, GenerationXrayClient.DRM),
                        configHolder.getConfig().seed);
                this.loader = new OreChunkLoader(this.vWorld, palettesFactory);
                
                this.pos = null;
            }
        }
        
        //v chunk region
        if (configHolder.getConfig().loadEnabled) {
            //reload
            ChunkPos playerPos = player.getChunkPos();
            if (pos == null || this.loadRadius == -1
                 ||playerPos.toLong() != pos.toLong()
                 || configHolder.getConfig().loadRadius != this.loadRadius) {
                this.regionFuture = loader.loadChunks(playerPos, configHolder.getConfig().loadRadius);
                this.pos = player.getChunkPos();
                this.loadRadius = configHolder.getConfig().loadRadius;
            }
        } else {
            this.pos = null;
            this.regionFuture = null;
            this.renderData = null;
            this.loader.clearCache();
        }

        //ore render data
        if (regionFuture != null &&
        regionFuture.isDone() &&
        !regionFuture.isCancelled() &&
        !regionFuture.isCompletedExceptionally()) {
            //generate render data
            if (regionFuture.join() == null) {
                LOGGER.warn("render data update failed [{}, {}]", pos.x, pos.z);
                return;
            }
            
                OreType[] types;
                if (dimensionKey == World.OVERWORLD) {
                    types = OreType.overworldValues();
                } else {
                    types = OreType.netherValues();
                }

                Map<LongCollection, OreRenderOption> renderData = new HashMap<>();

                int vvd = configHolder.getConfig().verticalViewDistance;
                int startSectionY = (player.getBlockY() >> 4) - vvd;
                int endSectionY = (player.getBlockY() >> 4) + vvd;
                for(OreType type : types) {
                    if (!configHolder.getConfig().getEnabled(type)) {
                        continue;
                    }

                    LongCollection collection = new LongArrayList();

                    int outlineArgb = configHolder.getConfig().outlineAlpha << 24;
                    if (configHolder.getConfig().outlineColorEnabled) {
                        outlineArgb += configHolder.getConfig().getArgb(type) & 0x00ffffff;
                    } else {
                        outlineArgb += configHolder.getConfig().outlineRgb & 0x00ffffff;
                    }

                    OreRenderOption option = new OreRenderOption(
                            configHolder.getConfig().getArgb(type),
                            outlineArgb,
                            configHolder.getConfig().outlineEnabled,
                            configHolder.getConfig().boxEnabled);

                    regionFuture.join().forEach(holder -> {
                        if (vvd == -1) {
                            collection.addAll(holder.getOreData(type));
                        } else {
                            collection.addAll(holder.getOreData(type, startSectionY, endSectionY));
                        }
                    });

                    renderData.put(collection, option);
                }
                this.renderData = renderData;
        }
        
        //load status data
        if (regionFuture == null) {
            this.regionLoadStatus = LoadStatus.LOAD_DISABLED;
        } else if (regionFuture.isCompletedExceptionally()) {
            this.regionLoadStatus = LoadStatus.COMPLETED_EXCEPTIONALLY;
        } else if (regionFuture.isCancelled()) {
            this.regionLoadStatus = LoadStatus.CANCELLED;
        } else if (regionFuture.isDone()) {
            this.regionLoadStatus = LoadStatus.DONE;
        } else {
            this.regionLoadStatus = LoadStatus.IS_WORKING;
        }
    }
    
    public Map<LongCollection, OreRenderOption> getOreRenderData() {
        if (this.renderData != null) {
            return renderData;
        }
        return Map.of();
    }
    
    public LoadStatus getLoadStatus() {
        return this.regionLoadStatus;
    }
    
    private static RegistryKey<World> getDimensionKey(ClientWorld world) {
        if (world.getDimension().skybox() == DimensionType.Skybox.OVERWORLD) {
            return World.OVERWORLD;
        }
        if (world.getDimension().cardinalLightType() == DimensionType.CardinalLightType.NETHER) {
            return World.NETHER;
        }
        return World.END;
    }
    
    public enum LoadStatus {
        LOAD_DISABLED,
        IS_WORKING, 
        DONE, 
        CANCELLED, 
        COMPLETED_EXCEPTIONALLY;
    }
}
