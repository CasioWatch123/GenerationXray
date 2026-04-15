package com.generationxray.mixin.client.ores;

import com.casiowatch123.vchunklib.generation.virtual.world.chunk.VChunkRegion;
import com.generationxray.world.OreChunkLoader;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(OreFeature.class)
public class OreFeatureMixin {

    @Inject(
            method = "generateVeinPart",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/chunk/ChunkSection;setBlockState(IIILnet/minecraft/block/BlockState;Z)Lnet/minecraft/block/BlockState;",
                    shift = At.Shift.BEFORE
            )
    )
    private void setBlockStateBefore(
            StructureWorldAccess world, 
            Random random, 
            OreFeatureConfig config, 
            double startX, double endX, 
            double startZ, double endZ, 
            double startY, double endY, 
            int x, int y, int z, 
            int horizontalSize, int verticalSize, 
            CallbackInfoReturnable<Boolean> cir,

            @Local BlockPos.Mutable pos,
            @Local OreFeatureConfig.Target target)  {
        if (world instanceof VChunkRegion) {
            OreChunkLoader.registerOre(target.state, pos.asLong());
        }
    }
}
