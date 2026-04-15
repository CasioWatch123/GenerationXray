package com.generationxray.config.handler;

import com.generationxray.GenerationXray;
import com.generationxray.config.GenerationXrayConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class GenConfigHandler extends AbstractConfigHandler {
    public GenConfigHandler(ConfigHolder<GenerationXrayConfig> configHolder) {
        super(configHolder);
    }
    
    public void displayRadius() {
        displayMessage(
                Text.literal(PREFIX + "load radius: ")
                        .append(Text.literal(String.valueOf(configHolder.getConfig().loadRadius))
                                .formatted(Formatting.GREEN))
        );
    }
    
    public void setGenRadius(int value) {
        if (value >= GenerationXrayConfig.ConfigConstants.MIN_LOAD_RADIUS &&
                value <= GenerationXrayConfig.ConfigConstants.MAX_LOAD_RADIUS) {
            configHolder.getConfig().loadRadius = value;
            configHolder.save();
            displayMessage(
                    Text.literal(PREFIX + "Changed load radius: ")
                            .append(Text.literal(String.valueOf(value))
                                    .formatted(Formatting.GREEN))
            );
        } else {
            displayMessage(
                    Text.literal(PREFIX + "wrong argument: int ")
                            .append(Text.literal("["))
                            .append(Text.literal(GenerationXrayConfig.ConfigConstants.MIN_LOAD_RADIUS+" ")
                                    .formatted(Formatting.GREEN))
                            .append(Text.literal("~"))
                            .append(Text.literal(" "+GenerationXrayConfig.ConfigConstants.MAX_LOAD_RADIUS)
                                    .formatted(Formatting.GREEN))
                            .append(Text.literal("]")));
        }
    }
    
    public void displayGenEnabled() {
        displayMessage(
                Text.literal(PREFIX + "load enabled: ")
                        .append(configHolder.getConfig().loadEnabled ?
                                Text.literal("true").formatted(Formatting.GREEN)
                                : Text.literal("false").formatted(Formatting.RED))
        );
    }
    public void genEnable() {
        if (!configHolder.getConfig().loadEnabled) {
            configHolder.getConfig().loadEnabled = true;
            configHolder.save();
            displayMessage(
                    Text.literal(PREFIX + "Changed load enabled: ")
                            .append(Text.literal("true")
                                    .formatted(Formatting.DARK_GREEN))
            );
        }
    }
    
    public void genDisable() {
        if (configHolder.getConfig().loadEnabled) {
            configHolder.getConfig().loadEnabled = false;
            configHolder.save();
            displayMessage(
                    Text.literal(PREFIX + "Changed load enabled: ")
                            .append(Text.literal("false")
                                    .formatted(Formatting.DARK_RED))
            );
        }
    }
    
    public void displaySeed() {
        displayMessage(
                Text.literal(PREFIX + "seed: ")
                        .append(Text.literal(String.valueOf(configHolder.getConfig().seed)).formatted(Formatting.GREEN))
        );
    }
}
