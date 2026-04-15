package com.generationxray.config.command;

import com.generationxray.config.GenerationXrayConfig;
import com.generationxray.config.handler.GenConfigHandler;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.shedaniel.autoconfig.ConfigHolder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class GenerationCommand implements CommandComponent {
    private final LiteralArgumentBuilder<FabricClientCommandSource> genCommandRoot = ClientCommandManager.literal("load");

    public GenerationCommand(ConfigHolder<GenerationXrayConfig> configHolder, GenConfigHandler handler) {
        genCommandRoot
                .then(ClientCommandManager.literal("radius")
                        .executes(ctx -> {
                            handler.displayRadius();
                            return 1;
                        })
                        .then(ClientCommandManager.argument("number", IntegerArgumentType.integer())
                                .executes(ctx -> {
                                    int value = IntegerArgumentType.getInteger(ctx, "number");
                                    handler.setGenRadius(value);
                                    return 1;
                                })))
                .then(ClientCommandManager.literal("enable")
                        .executes(ctx -> {
                            handler.displayGenEnabled();
                            return 1;
                        })
                        .then(ClientCommandManager.literal("true").executes(ctx -> {
                            handler.genEnable();
                            return 1;
                        }))
                        .then(ClientCommandManager.literal("false").executes(ctx -> {
                            handler.genDisable();
                            return 1;
                        })))
                .then(ClientCommandManager.literal("seed")
                        .executes(ctx -> {
                            handler.displaySeed();
                            return 1;
                        }));
    }
    @Override
    public void attachTo(LiteralArgumentBuilder<FabricClientCommandSource> parent) {
        parent.then(genCommandRoot);
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> getRoot() {
        return genCommandRoot;
    }
}
