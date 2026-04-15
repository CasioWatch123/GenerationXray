package com.generationxray;

import com.casiowatch123.vchunklib.generation.virtual.VUtils;
import com.generationxray.config.GenerationXrayConfig;
import com.generationxray.config.command.GenerationCommand;
import com.generationxray.config.command.RenderCommand;
import com.generationxray.config.handler.GenConfigHandler;
import com.generationxray.config.handler.RenderConfigHandler;
import com.generationxray.render.HudRenderer;
import com.generationxray.render.OverlayRenderer;
import com.generationxray.world.OreChunkLoader;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.InputUtil;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

import java.util.concurrent.ForkJoinPool;

import static com.generationxray.GenerationXray.MOD_ID;

public class GenerationXrayClient implements ClientModInitializer {
    public static Logger LOGGER = GenerationXray.LOGGER;
    public static DynamicRegistryManager DRM;
    
	@Override
	public void onInitializeClient() {
        ConfigHolder<GenerationXrayConfig> configHolder = AutoConfig.register(GenerationXrayConfig.class,
                GsonConfigSerializer::new);

        LiteralArgumentBuilder<FabricClientCommandSource> rootCommand = ClientCommandManager.literal("gx");
        RenderConfigHandler renderConfigHandler = new RenderConfigHandler(configHolder);
        new RenderCommand(configHolder, renderConfigHandler).attachTo(rootCommand);
        GenConfigHandler genConfigHandler = new GenConfigHandler(configHolder);
        new GenerationCommand(configHolder, genConfigHandler).attachTo(rootCommand);
        
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(rootCommand);
        });

        KeyBinding toggleKey = KeyBindingHelper.registerKeyBinding(
                new KeyBinding(
                        "key.generationxray.toggle",
                        InputUtil.UNKNOWN_KEY.getCode(), 
                        new KeyBinding.Category(Identifier.of("generationxray", "modname")))
        );
        
        ClientLifecycleEvents.CLIENT_STARTED.register(c -> {
            ForkJoinPool.commonPool().execute(() -> {
                DRM = VUtils.createVanillaRegistryManager();
            });
        });
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean flag = false;
            while(toggleKey.wasPressed()) {
                flag = true;
            }
            if (flag) {
                if (configHolder.getConfig().renderEnabled) {
                    renderConfigHandler.renderDisable();
                } else {
                    renderConfigHandler.renderEnable();
                }
            }
        });
        
        GenerationXrayTickService tickService = new GenerationXrayTickService(configHolder);

        ClientTickEvents.END_CLIENT_TICK.register(tickService::updateTick);

        WorldRenderEvents.END_MAIN .register(ctx -> {
            OverlayRenderer.render(ctx, tickService.getOreRenderData());
        });
        
        HudElementRegistry.attachElementAfter(
                VanillaHudElements.HOTBAR,
                Identifier.of(MOD_ID, "loadstatus"),
                (DrawContext ctx, RenderTickCounter tracker) -> {
                    HudRenderer.render(ctx, tickService.getLoadStatus());
                }
        );

        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
            configHolder.save();
            OreChunkLoader.shutdown();
        });
    }
}