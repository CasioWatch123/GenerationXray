package com.generationxray.config.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;


public interface CommandComponent {
    void attachTo(LiteralArgumentBuilder<FabricClientCommandSource> parent);
    
    LiteralArgumentBuilder<FabricClientCommandSource> getRoot();
}
