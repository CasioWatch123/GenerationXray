package com.generationxray.config.command;

import com.generationxray.config.GenerationXrayConfig;
import com.generationxray.config.handler.RenderConfigHandler;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.shedaniel.autoconfig.ConfigHolder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

public class RenderCommand implements CommandComponent {
    private final LiteralArgumentBuilder<FabricClientCommandSource> renderCommandRoot = ClientCommandManager.literal("render");
    public RenderCommand(ConfigHolder<GenerationXrayConfig> configHolder, RenderConfigHandler renderConfigHandler) {
        renderCommandRoot
                .then(ClientCommandManager.literal("enable")
                        .executes(ctx -> {
                            renderConfigHandler.displayRenderEnabled();
                            return 1;
                        })
                        .then(ClientCommandManager.literal("true").executes(ctx -> {
                            renderConfigHandler.renderEnable();
                            return 1;
                        }))
                        .then(ClientCommandManager.literal("false").executes(ctx -> {
                            renderConfigHandler.renderDisable();
                            return 1;
                        })))
                .then(ClientCommandManager.literal("outline")
                        .executes(ctx -> {
                            renderConfigHandler.displayOutlineEnabled();
                            return 1;
                        })
                        .then(ClientCommandManager.literal("true").executes(ctx -> {
                            renderConfigHandler.outlineEnable();
                            return 1;
                        }))
                        .then(ClientCommandManager.literal("false").executes(ctx -> {
                            renderConfigHandler.outlineDisable();
                            return 1;
                        }))
                        .then(ClientCommandManager.literal("color")
                                .executes(ctx -> {
                                    renderConfigHandler.displayOutlineColorEnabled();
                                    return 1;
                                })
                                .then(ClientCommandManager.literal("true").executes(ctx -> {
                                    renderConfigHandler.outlineColorEnable();
                                    return 1;
                                }))
                                .then(ClientCommandManager.literal("false").executes(ctx -> {
                                    renderConfigHandler.outlineColorDisable();
                                    return 1;
                                }))))
                .then(ClientCommandManager.literal("box")
                        .executes(ctx -> {
                            renderConfigHandler.displayBoxEnabled();
                            return 1;
                        })
                        .then(ClientCommandManager.literal("true").executes(ctx -> {
                            renderConfigHandler.boxEnable();
                            return 1;
                        }))
                        .then(ClientCommandManager.literal("false").executes(ctx -> {
                            renderConfigHandler.boxDisable();
                            return 1;
                        })))
                
                .then(ClientCommandManager.literal("vdistance")
                        .executes(ctx -> {
                            renderConfigHandler.displayVDistance();
                            return 1;
                        })
                        .then(ClientCommandManager.argument("number", IntegerArgumentType.integer())
                                .executes(ctx -> {
                                        int value = IntegerArgumentType.getInteger(ctx, "number");
                                        renderConfigHandler.setVDistance(value);
                                        return 1;
                        })));
    }
    @Override
    public void attachTo(LiteralArgumentBuilder<FabricClientCommandSource> parent) {
        parent.then(renderCommandRoot);
    }

    @Override
    public LiteralArgumentBuilder<FabricClientCommandSource> getRoot() {
        return renderCommandRoot;
    }
}
