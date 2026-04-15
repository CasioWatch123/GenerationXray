package com.generationxray.config.command;

import com.generationxray.config.GenerationXrayConfig;
import com.generationxray.config.handler.TestCommandHandler;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.shedaniel.autoconfig.ConfigHolder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class TestCommand implements CommandComponent {
    private final LiteralArgumentBuilder<FabricClientCommandSource> testCommandRoot = ClientCommandManager.literal("test");
    
    public TestCommand(ConfigHolder<GenerationXrayConfig> configHolder, TestCommandHandler handler) {
    }
    @Override
    public void attachTo(LiteralArgumentBuilder<FabricClientCommandSource> parent) {
        parent.then(testCommandRoot);
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> getRoot() {
        return testCommandRoot;
    }
}
