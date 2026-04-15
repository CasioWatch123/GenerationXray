package com.generationxray.render;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;

public class RenderLayers {
    private static final RenderPipeline.Snippet FOGLESS_LINES_SNIPPET = RenderPipeline
            .builder(RenderPipelines.TRANSFORMS_PROJECTION_FOG_SNIPPET,
                    RenderPipelines.GLOBALS_SNIPPET)
            .withVertexShader(Identifier.of("generationxray:fogless_lines"))
            .withFragmentShader(Identifier.of("generationxray:fogless_lines"))
            .withBlend(BlendFunction.TRANSLUCENT).withCull(false)
            .withVertexFormat(VertexFormats.POSITION_COLOR_NORMAL_LINE_WIDTH,
                    VertexFormat.DrawMode.LINES)
            .buildSnippet();

    public static final RenderPipeline ESP_LINES_PIPELINE =
            RenderPipelines.register(RenderPipeline.builder(FOGLESS_LINES_SNIPPET)
                    .withLocation(Identifier.of("generationxray:pipeline/esp_lines"))
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).build());

    public static final RenderPipeline ESP_QUADS_PIPELINE = RenderPipelines
            .register(RenderPipeline.builder(RenderPipelines.POSITION_COLOR_SNIPPET)
                    .withLocation(Identifier.of("generationxray:pipeline/esp_quads"))
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST).build());

    public static final RenderLayer ESP_LINES =
            RenderLayer.of("chestesp:esp_lines",
                    RenderSetup.builder(ESP_LINES_PIPELINE)
                            .layeringTransform(LayeringTransform.VIEW_OFFSET_Z_LAYERING)
                            .outputTarget(OutputTarget.ITEM_ENTITY_TARGET)
                            .build());

    public static final RenderLayer ESP_QUADS = RenderLayer.of(
            "chestesp:esp_quads", RenderSetup.builder(ESP_QUADS_PIPELINE)
                    .translucent().build());
}
