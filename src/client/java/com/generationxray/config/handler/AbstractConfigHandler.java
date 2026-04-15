package com.generationxray.config.handler;

import com.generationxray.GenerationXray;
import com.generationxray.config.GenerationXrayConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;

public abstract class AbstractConfigHandler {
    protected static final String PREFIX = "(" + GenerationXray.MOD_ID + ") ";
    protected final ConfigHolder<GenerationXrayConfig> configHolder;
    protected AbstractConfigHandler(ConfigHolder<GenerationXrayConfig> configHolder) {
        this.configHolder = configHolder;
    }

    protected static void displayMessage(Text text) {
        ChatHud chatHud = MinecraftClient.getInstance().inGameHud.getChatHud();
        if (chatHud == null) {
            return;
        }

        chatHud.addMessage(text);
    }
}
