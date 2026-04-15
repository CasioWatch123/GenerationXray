package com.generationxray.render;

import com.generationxray.config.GenerationXrayConfig;
import it.unimi.dsi.fastutil.longs.LongCollection;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Map;

public class OverlayRenderer {
    private static final double MAX_VIEW_DISTANCE = 160;
    private static final float LINE_WIDTH = 1.0f;
    private static void drawSolidBox(
            MatrixStack matrices, VertexConsumer buffer,
            float x1, float y1, float z1,
            float x2, float y2, float z2, int rgba)
    {
        MatrixStack.Entry entry = matrices.peek();

        buffer.vertex(entry, x1, y1, z1).color(rgba);
        buffer.vertex(entry, x2, y1, z1).color(rgba);
        buffer.vertex(entry, x2, y1, z2).color(rgba);
        buffer.vertex(entry, x1, y1, z2).color(rgba);

        buffer.vertex(entry, x1, y2, z1).color(rgba);
        buffer.vertex(entry, x1, y2, z2).color(rgba);
        buffer.vertex(entry, x2, y2, z2).color(rgba);
        buffer.vertex(entry, x2, y2, z1).color(rgba);

        buffer.vertex(entry, x1, y1, z1).color(rgba);
        buffer.vertex(entry, x1, y2, z1).color(rgba);
        buffer.vertex(entry, x2, y2, z1).color(rgba);
        buffer.vertex(entry, x2, y1, z1).color(rgba);

        buffer.vertex(entry, x2, y1, z1).color(rgba);
        buffer.vertex(entry, x2, y2, z1).color(rgba);
        buffer.vertex(entry, x2, y2, z2).color(rgba);
        buffer.vertex(entry, x2, y1, z2).color(rgba);

        buffer.vertex(entry, x1, y1, z2).color(rgba);
        buffer.vertex(entry, x2, y1, z2).color(rgba);
        buffer.vertex(entry, x2, y2, z2).color(rgba);
        buffer.vertex(entry, x1, y2, z2).color(rgba);

        buffer.vertex(entry, x1, y1, z1).color(rgba);
        buffer.vertex(entry, x1, y1, z2).color(rgba);
        buffer.vertex(entry, x1, y2, z2).color(rgba);
        buffer.vertex(entry, x1, y2, z1).color(rgba);
    }

    private static void drawOutlinedBox(
            MatrixStack matrices, VertexConsumer buffer, 
            float x1, float y1, float z1, 
            float x2, float y2, float z2, int argb)
    {
        MatrixStack.Entry entry = matrices.peek();

        // bottom lines
        buffer.vertex(entry, x1, y1, z1).color(argb).normal(entry, 1, 0, 0).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x2, y1, z1).color(argb).normal(entry, 1, 0, 0).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x1, y1, z1).color(argb).normal(entry, 0, 0, 1).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x1, y1, z2).color(argb).normal(entry, 0, 0, 1).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x2, y1, z1).color(argb).normal(entry, 0, 0, 1).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x2, y1, z2).color(argb).normal(entry, 0, 0, 1).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x1, y1, z2).color(argb).normal(entry, 1, 0, 0).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x2, y1, z2).color(argb).normal(entry, 1, 0, 0).lineWidth(LINE_WIDTH);

        // top lines
        buffer.vertex(entry, x1, y2, z1).color(argb).normal(entry, 1, 0, 0).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x2, y2, z1).color(argb).normal(entry, 1, 0, 0).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x1, y2, z1).color(argb).normal(entry, 0, 0, 1).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x1, y2, z2).color(argb).normal(entry, 0, 0, 1).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x2, y2, z1).color(argb).normal(entry, 0, 0, 1).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x2, y2, z2).color(argb).normal(entry, 0, 0, 1).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x1, y2, z2).color(argb).normal(entry, 1, 0, 0).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x2, y2, z2).color(argb).normal(entry, 1, 0, 0).lineWidth(LINE_WIDTH);

        // side lines
        buffer.vertex(entry, x1, y1, z1).color(argb).normal(entry, 0, 1, 0).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x1, y2, z1).color(argb).normal(entry, 0, 1, 0).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x2, y1, z1).color(argb).normal(entry, 0, 1, 0).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x2, y2, z1).color(argb).normal(entry, 0, 1, 0).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x1, y1, z2).color(argb).normal(entry, 0, 1, 0).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x1, y2, z2).color(argb).normal(entry, 0, 1, 0).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x2, y1, z2).color(argb).normal(entry, 0, 1, 0).lineWidth(LINE_WIDTH);
        buffer.vertex(entry, x2, y2, z2).color(argb).normal(entry, 0, 1, 0).lineWidth(LINE_WIDTH);
    }
    
    public static void render(WorldRenderContext ctx, Map<LongCollection, OreRenderOption> entryMap) {
        if (entryMap == null) {
            return;
        }
        VertexConsumerProvider vcp = ctx.consumers();

        Vec3d camPos = ctx.gameRenderer().getCamera().getCameraPos();
        Vec3d camOffset = camPos.negate();

        BlockPos.Mutable mutable = new BlockPos.Mutable();

        entryMap.forEach((collection, option) -> {
            VertexConsumer buffer;
            if (option.solidEnabled()) {
                buffer = vcp.getBuffer(RenderLayers.ESP_QUADS);
                for (long pos : collection) {
                    mutable.set(pos);

                    if (isOutOfViewDistance(mutable, camPos)) {
                        continue;
                    }

                    float x1 = (float) (mutable.getX() + camOffset.x);
                    float y1 = (float) (mutable.getY() + camOffset.y);
                    float z1 = (float) (mutable.getZ() + camOffset.z);
                    float x2 = x1 + 1;
                    float y2 = y1 + 1;
                    float z2 = z1 + 1;
                    drawSolidBox(
                            ctx.matrices(), buffer,
                            x1, y1, z1,
                            x2, y2, z2,
                            option.solidRgba());
                    
                }
            }
            if (option.outlineEnabled()) {
                buffer = vcp.getBuffer(RenderLayers.ESP_LINES);
                for (long pos : collection) {
                    mutable.set(pos);

                    if (isOutOfViewDistance(mutable, camPos)) {
                        continue;
                    }

                    float x1 = (float) (mutable.getX() + camOffset.x);
                    float y1 = (float) (mutable.getY() + camOffset.y);
                    float z1 = (float) (mutable.getZ() + camOffset.z);
                    float x2 = x1 + 1;
                    float y2 = y1 + 1;
                    float z2 = z1 + 1;

                    drawOutlinedBox(
                            ctx.matrices(), buffer,
                            x1, y1, z1,
                            x2, y2, z2,
                            option.outlineRgba());
                }
            }
        });
    }
    
    private static boolean isOutOfViewDistance(BlockPos pos, Vec3d camPos) {
        double cx = pos.getX() + 0.5;
        double cz = pos.getZ() + 0.5;

        double distanceX = Math.abs(cx - camPos.x);
        double distanceZ = Math.abs(cz - camPos.z);

        return Math.max(distanceX, distanceZ) > MAX_VIEW_DISTANCE;
    }
}
