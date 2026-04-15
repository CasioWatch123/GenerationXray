package com.generationxray.mixin.client.ores;

import com.casiowatch123.vchunklib.generation.virtual.world.gen.VStructureAccessor;
import com.generationxray.world.OreChunkLoader;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.noise.NoiseConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseChunkGenerator.class)
public class OreVeinMixin {
    @Inject(
            method = "populateNoise(" +
                    "Lnet/minecraft/world/gen/chunk/Blender;" +
                    "Lnet/minecraft/world/gen/StructureAccessor;" +
                    "Lnet/minecraft/world/gen/noise/NoiseConfig;" +
                    "Lnet/minecraft/world/chunk/Chunk;" +
                    "II)" +
                    "Lnet/minecraft/world/chunk/Chunk;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/chunk/ChunkSection;" +
                            "setBlockState(IIILnet/minecraft/block/BlockState;Z)" +
                            "Lnet/minecraft/block/BlockState;",
                    shift = At.Shift.BEFORE
            )
    )
    private void setBlockStateBefore(
            Blender blender,
            StructureAccessor structureAccessor,
            NoiseConfig noiseConfig,
            Chunk chunk,
            int minimumCellY, int cellHeight,
            CallbackInfoReturnable<Chunk> cir,
            
            @Local(ordinal = 17) int x, @Local(ordinal = 13) int y, @Local(ordinal = 20) int z, 
            @Local BlockState blockState
            ) {
        if (structureAccessor instanceof VStructureAccessor) {
            if (blockState.isOf(Blocks.COPPER_ORE) || blockState.isOf(Blocks.DEEPSLATE_COPPER_ORE) || 
                blockState.isOf(Blocks.IRON_ORE) || blockState.isOf(Blocks.DEEPSLATE_IRON_ORE) || 
                blockState.isOf(Blocks.RAW_COPPER_BLOCK) || blockState.isOf(Blocks.RAW_IRON_BLOCK))
            {
                OreChunkLoader.registerOre(blockState, BlockPos.asLong(x, y, z));
            }
        }
    }
}
