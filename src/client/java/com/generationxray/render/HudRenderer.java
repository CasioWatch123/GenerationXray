package com.generationxray.render;

import com.generationxray.GenerationXrayTickService;
import com.generationxray.config.GenerationXrayConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class HudRenderer {
    public static void render(DrawContext ctx, GenerationXrayTickService.LoadStatus status) {
        if (!AutoConfig.getConfigHolder(GenerationXrayConfig.class).getConfig().regionLoadIndicatorEnabled) {
            return;
        }
        
        Text text = Text.literal(status.name());

        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
        ctx.drawText(
                renderer,
                text,
                2, ctx.getScaledWindowHeight() - renderer.fontHeight - 1, 0XFFFFFFFF, 
                true
        );
    }
}
