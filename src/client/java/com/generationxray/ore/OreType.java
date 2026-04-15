package com.generationxray.ore;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import org.jetbrains.annotations.Nullable;

public enum OreType {
    ANDESITE(Blocks.ANDESITE), 
    CLAY(Blocks.CLAY), 
    COAL(Blocks.COAL_ORE), 
    DEEPSLATE_COAL(Blocks.DEEPSLATE_COAL_ORE), 
    COPPER(Blocks.COPPER_ORE), 
    DEEPSLATE_COPPER(Blocks.DEEPSLATE_COPPER_ORE), 
    DIAMOND(Blocks.DIAMOND_ORE), 
    DEEPSLATE_DIAMOND(Blocks.DEEPSLATE_DIAMOND_ORE), 
    DIORITE(Blocks.DIORITE), 
    DIRT(Blocks.DIRT), 
    EMERALD(Blocks.EMERALD_ORE), 
    DEEPSLATE_EMERALD(Blocks.DEEPSLATE_EMERALD_ORE), 
    GOLD(Blocks.GOLD_ORE), 
    DEEPSLATE_GOLD(Blocks.DEEPSLATE_GOLD_ORE), 
    GRANITE(Blocks.GRANITE), 
    GRAVEL(Blocks.GRAVEL), 
    INFESTED(Blocks.INFESTED_STONE),
    DEEPSLATE_INFESTED(Blocks.INFESTED_DEEPSLATE), 
    IRON(Blocks.IRON_ORE), 
    DEEPSLATE_IRON(Blocks.DEEPSLATE_IRON_ORE), 
    LAPIS(Blocks.LAPIS_ORE), 
    DEEPSLATE_LAPIS(Blocks.DEEPSLATE_LAPIS_ORE), 
    REDSTONE(Blocks.REDSTONE_ORE), 
    DEEPSLATE_REDSTONE(Blocks.DEEPSLATE_REDSTONE_ORE), 
    TUFF(Blocks.TUFF), 
    RAW_IRON_BLOCK(Blocks.RAW_IRON_BLOCK), 
    RAW_COPPER_BLOCK(Blocks.RAW_COPPER_BLOCK),

    ANCIENT_DEBRIS(Blocks.ANCIENT_DEBRIS),
    BLACKSTONE(Blocks.BLACKSTONE),
    MAGMA_BLOCK(Blocks.MAGMA_BLOCK),
    NETHER_GOLD(Blocks.NETHER_GOLD_ORE),
    NETHER_QUARTZ(Blocks.NETHER_QUARTZ_ORE),
    SOUL_SAND(Blocks.SOUL_SAND);

    public final Block block;
    private static final OreType[] OVERWORLD_ORES = {
            ANDESITE, 
            CLAY,
            COAL,
            DEEPSLATE_COAL,
            COPPER,
            DEEPSLATE_COPPER,
            DIAMOND,
            DEEPSLATE_DIAMOND,
            DIORITE,
            DIRT,
            EMERALD,
            DEEPSLATE_EMERALD,
            GOLD,
            DEEPSLATE_GOLD,
            GRANITE,
            GRAVEL,
            INFESTED,
            DEEPSLATE_INFESTED,
            IRON,
            DEEPSLATE_IRON,
            LAPIS,
            DEEPSLATE_LAPIS,
            REDSTONE,
            DEEPSLATE_REDSTONE,
            TUFF,
            RAW_IRON_BLOCK,
            RAW_COPPER_BLOCK};
    
    private static final OreType[] NETHER_ORES = {
            ANCIENT_DEBRIS,
            BLACKSTONE,
            MAGMA_BLOCK,
            NETHER_GOLD,
            NETHER_QUARTZ,
            SOUL_SAND, 
            GRAVEL};
    
    public static OreType[] overworldValues() {
        return OVERWORLD_ORES;
    }
    public static OreType[] netherValues() {
        return NETHER_ORES;
    }
    
    @Nullable
    public static OreType getOreTypeOrNull(BlockState blockState) {
        for(OreType type : OreType.values()) {
            if (blockState.isOf(type.block)) {
                return type;
            }
        }

        return null;
    }
    
    
    
    OreType(Block block) {
        this.block = block;
    }
}
