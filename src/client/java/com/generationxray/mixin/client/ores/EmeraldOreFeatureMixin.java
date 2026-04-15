package com.generationxray.mixin.client.ores;

import com.casiowatch123.vchunklib.generation.virtual.world.chunk.VChunkRegion;
import com.generationxray.world.OreChunkLoader;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.EmeraldOreFeature;
import net.minecraft.world.gen.feature.EmeraldOreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EmeraldOreFeature.class)
public class EmeraldOreFeatureMixin {
    @Inject(
            method = "generate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/StructureWorldAccess;" +
                            "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z",
                    shift = At.Shift.BEFORE
            )
    )
    private void setBlockStateBefore(
            FeatureContext<EmeraldOreFeatureConfig> context, 
            CallbackInfoReturnable<Boolean> cir, 
            @Local OreFeatureConfig.Target target, 
            @Local BlockPos pos) {
        if (context.getWorld() instanceof VChunkRegion) {
            OreChunkLoader.registerOre(target.state, pos.asLong());
        }
    }
}
