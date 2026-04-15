package com.generationxray.config.handler;

import com.generationxray.GenerationXray;
import com.generationxray.config.GenerationXrayConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class RenderConfigHandler extends AbstractConfigHandler{
    public RenderConfigHandler(ConfigHolder<GenerationXrayConfig> configHolder) {
        super(configHolder);
    }
    public void displayRenderEnabled() {
        displayMessage(Text.literal(PREFIX + "render enable: ")
                .append(configHolder.getConfig().renderEnabled ?
                        Text.literal("true").formatted(Formatting.GREEN)
                        : Text.literal("false").formatted(Formatting.RED)));
    }
    
    public void renderEnable() {
        if (!configHolder.getConfig().renderEnabled) {
            configHolder.getConfig().renderEnabled = true;
            configHolder.save();
            displayMessage(
                    Text.literal(PREFIX + "Changed render enable: ")
                            .append(Text.literal("true")
                                    .formatted(Formatting.DARK_GREEN))
            );
        }
    }
    
    public void renderDisable() {
        if (configHolder.getConfig().renderEnabled) {
            configHolder.getConfig().renderEnabled = false;
            configHolder.save();
            displayMessage(
                    Text.literal(PREFIX + "Changed render enable: ")
                            .append(Text.literal("false")
                                    .formatted(Formatting.DARK_RED))
            );
        }
    }
    
    public void displayOutlineEnabled() {
        displayMessage(
                Text.literal(PREFIX + "render outline: ")
                        .append(configHolder.getConfig().outlineEnabled ?
                                Text.literal("true").formatted(Formatting.GREEN)
                                : Text.literal("false").formatted(Formatting.RED))
        );
    }
    
    public void outlineEnable() {
        if (!configHolder.getConfig().outlineEnabled) {
            configHolder.getConfig().outlineEnabled = true;
            configHolder.save();
            displayMessage(
                    Text.literal(PREFIX + "Changed render outline: ")
                            .append(Text.literal("true")
                                    .formatted(Formatting.DARK_GREEN))
            );
        }
    }
    
    public void outlineDisable() {
        if (configHolder.getConfig().outlineEnabled) {
            configHolder.getConfig().outlineEnabled = false;
            configHolder.save();
            displayMessage(
                    Text.literal(PREFIX + "Changed render outline: ")
                            .append(Text.literal("false")
                                    .formatted(Formatting.DARK_RED))
            );
        }
    }
    
    public void displayOutlineColorEnabled() {
        displayMessage(
                Text.literal(PREFIX + "render outline color: ")
                        .append(configHolder.getConfig().outlineColorEnabled ?
                                Text.literal("true").formatted(Formatting.GREEN)
                                : Text.literal("false").formatted(Formatting.RED))
        );
    }
    
    public void outlineColorEnable() {
        if (!configHolder.getConfig().outlineColorEnabled) {
            configHolder.getConfig().outlineColorEnabled = true;
            configHolder.save();
            displayMessage(
                    Text.literal(PREFIX + "Changed render outline color: ")
                            .append(Text.literal("true")
                                    .formatted(Formatting.DARK_GREEN))
            );
        }
    }
    
    public void outlineColorDisable() {
        if (configHolder.getConfig().outlineColorEnabled) {
            configHolder.getConfig().outlineColorEnabled = false;
            configHolder.save();
            displayMessage(
                    Text.literal(PREFIX + "Changed render outline color: ")
                            .append(Text.literal("false")
                                    .formatted(Formatting.DARK_RED))
            );
        }
    }
    
    public void displayBoxEnabled() {
        displayMessage(
                Text.literal(PREFIX + "render box: ")
                        .append(configHolder.getConfig().boxEnabled ?
                                Text.literal("true").formatted(Formatting.GREEN)
                                : Text.literal("false").formatted(Formatting.RED))
        );
    }
    
    public void boxEnable() {
        if (!configHolder.getConfig().boxEnabled) {
            configHolder.getConfig().boxEnabled = true;
            configHolder.save();
            displayMessage(
                    Text.literal(PREFIX + "Changed render box: ")
                            .append(Text.literal("true")
                                    .formatted(Formatting.DARK_GREEN))
            );
        }
    }
    
    public void boxDisable() {
        if (configHolder.getConfig().boxEnabled) {
            configHolder.getConfig().boxEnabled = false;
            configHolder.save();
            displayMessage(
                    Text.literal(PREFIX + "Changed render box: ")
                            .append(Text.literal("false")
                                    .formatted(Formatting.DARK_RED))
            );
        }
    }
    
    public void displayVDistance() {
        displayMessage(
                Text.literal(PREFIX + "vertical render distance: ")
                        .append(Text.literal(String.valueOf(configHolder.getConfig().verticalViewDistance))
                                .formatted(Formatting.GREEN))
        );
    }
    
    public void setVDistance(int value) {
        if (value >= GenerationXrayConfig.ConfigConstants.MIN_VERTICAL_VIEW_DISTANCE &&
                value <= GenerationXrayConfig.ConfigConstants.MAX_VERTICAL_VIEW_DISTANCE) {
            configHolder.getConfig().verticalViewDistance = value;
            configHolder.save();
            displayMessage(
                    Text.literal(PREFIX + "Changed vertical render distance: ")
                            .append(Text.literal(String.valueOf(value))
                                    .formatted(Formatting.GREEN))
            );
        } else {
            displayMessage(
                    Text.literal(PREFIX + "wrong argument: int ")
                            .append(Text.literal("["))
                            .append(Text.literal(GenerationXrayConfig.ConfigConstants.MIN_VERTICAL_VIEW_DISTANCE+" ")
                                    .formatted(Formatting.GREEN))
                            .append(Text.literal("~"))
                            .append(Text.literal(" "+GenerationXrayConfig.ConfigConstants.MAX_VERTICAL_VIEW_DISTANCE)
                                    .formatted(Formatting.GREEN))
                            .append(Text.literal("]")));
        }
    }
}
