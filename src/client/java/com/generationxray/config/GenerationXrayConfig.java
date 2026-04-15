package com.generationxray.config;

import com.generationxray.ore.OreType;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "generationxray")
public class GenerationXrayConfig implements ConfigData {
    @ConfigEntry.Category("render")
    public boolean renderEnabled = false;

    @ConfigEntry.Category("render")
    public boolean outlineEnabled = true;

    @ConfigEntry.Category("render")
    public boolean boxEnabled = true;

//    @ConfigEntry.Category("render")
//    public boolean serverWorldFiltering = true;
    
    @ConfigEntry.Category("render")
    @ConfigEntry.BoundedDiscrete(min = ConfigConstants.MIN_VERTICAL_VIEW_DISTANCE, max = ConfigConstants.MAX_VERTICAL_VIEW_DISTANCE)
    public int verticalViewDistance = -1;

    @ConfigEntry.Category("render")
    public boolean outlineColorEnabled = true;
    
    @ConfigEntry.Category("render")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 255)
    public int outlineAlpha = 70;
    
    @ConfigEntry.Category("render")
    @ConfigEntry.ColorPicker
    public int outlineRgb = 0X00333333;
    
    @ConfigEntry.Category("hud")
    public boolean regionLoadIndicatorEnabled = false;
    
    @ConfigEntry.Category("generation")
    @ConfigEntry.BoundedDiscrete(min = ConfigConstants.MIN_LOAD_RADIUS, max = ConfigConstants.MAX_LOAD_RADIUS)
    public int loadRadius = 2;

    @ConfigEntry.Category("generation")
    public boolean loadEnabled = true;
    
    @ConfigEntry.Category("generation")
    public long seed = 0;

    
    
    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int andesiteArgb = 0X50333333;
    @ConfigEntry.Category("oretype")
    public boolean andesiteEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int clayArgb = 0X509999CC;
    @ConfigEntry.Category("oretype")
    public boolean clayEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int coalArgb = 0X50111111;
    @ConfigEntry.Category("oretype")
    public boolean coalEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int deepslateCoalArgb = 0X50111111;
    @ConfigEntry.Category("oretype")
    public boolean deepslateCoalEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int copperArgb = 0X50FF6600;
    @ConfigEntry.Category("oretype")
    public boolean copperEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int deepslateCopperArgb = 0X50FF6600;
    @ConfigEntry.Category("oretype")
    public boolean deepslateCopperEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int diamondArgb = 0X5000FFFF;
    @ConfigEntry.Category("oretype")
    public boolean diamondEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int deepslateDiamondArgb = 0X5000FFFF;
    @ConfigEntry.Category("oretype")
    public boolean deepslateDiamondEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int dioriteArgb = 0X50CCCCCC;
    @ConfigEntry.Category("oretype")
    public boolean dioriteEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int dirtArgb = 0X50996633;
    @ConfigEntry.Category("oretype")
    public boolean dirtEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int emeraldArgb = 0X5033FF33;
    @ConfigEntry.Category("oretype")
    public boolean emeraldEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int deepslateEmeraldArgb = 0X5033FF33;
    @ConfigEntry.Category("oretype")
    public boolean deepslateEmeraldEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int goldArgb = 0X50FFFF33;
    @ConfigEntry.Category("oretype")
    public boolean goldEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int deepslateGoldArgb = 0X50FFFF33;
    @ConfigEntry.Category("oretype")
    public boolean deepslateGoldEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int graniteArgb = 0X50CC3300;
    @ConfigEntry.Category("oretype")
    public boolean graniteEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int gravelArgb = 0X50666699;
    @ConfigEntry.Category("oretype")
    public boolean gravelEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int infestedArgb = 0X50FF0099;
    @ConfigEntry.Category("oretype")
    public boolean infestedEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int deepslateInfestedArgb = 0X50FF0099;
    @ConfigEntry.Category("oretype")
    public boolean deepslateInfestedEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int ironArgb = 0X50FFCC99;
    @ConfigEntry.Category("oretype")
    public boolean ironEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int deepslateIronArgb = 0X50FFCC99;
    @ConfigEntry.Category("oretype")
    public boolean deepslateIronEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int lapisArgb = 0X503333CC;
    @ConfigEntry.Category("oretype")
    public boolean lapisEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int deepslateLapisArgb = 0X503333CC;
    @ConfigEntry.Category("oretype")
    public boolean deepslateLapisEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int redstoneArgb = 0X50990000;
    @ConfigEntry.Category("oretype")
    public boolean redstoneEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int deepslateRedstoneArgb = 0X50990000;
    @ConfigEntry.Category("oretype")
    public boolean deepslateRedstoneEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int tuffArgb = 0X50996633;
    @ConfigEntry.Category("oretype")
    public boolean tuffEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int rawIronBlockArgb = 0X50CC6633;
    @ConfigEntry.Category("oretype")
    public boolean rawIronBlockEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int rawCopperBlockArgb = 0X50FF3300;
    @ConfigEntry.Category("oretype")
    public boolean rawCopperBlockEnabled = false;


    
    
    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int ancientDebrisArgb = 0X5033FF33;
    @ConfigEntry.Category("oretype")
    public boolean ancientDebrisEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int blackStoneArgb = 0X50333333;
    @ConfigEntry.Category("oretype")
    public boolean blackStoneEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int magmaBlockArgb = 0X50FF6600;
    @ConfigEntry.Category("oretype")
    public boolean magmaBlockEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int netherGoldArgb = 0X50FFFF33;
    @ConfigEntry.Category("oretype")
    public boolean netherGoldEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int netherQuartzArgb = 0X50EEEEEE;
    @ConfigEntry.Category("oretype")
    public boolean netherQuartzEnabled = false;

    @ConfigEntry.Category("oretype")
    @ConfigEntry.ColorPicker(allowAlpha = true)
    public int soulSandArgb = 0X50112233;
    @ConfigEntry.Category("oretype")
    public boolean soulSandEnabled = false;
    
    public int getArgb(OreType type) {
        return switch (type) {
            case ANDESITE           -> andesiteArgb;
            case CLAY               -> clayArgb;
            case COAL               -> coalArgb;
            case DEEPSLATE_COAL     -> deepslateCoalArgb;
            case COPPER             -> copperArgb;
            case DEEPSLATE_COPPER   -> deepslateCopperArgb;
            case DIAMOND            -> diamondArgb;
            case DEEPSLATE_DIAMOND  -> deepslateDiamondArgb;
            case DIORITE            -> dioriteArgb;
            case DIRT               -> dirtArgb;
            case EMERALD            -> emeraldArgb;
            case DEEPSLATE_EMERALD  -> deepslateEmeraldArgb;
            case GOLD               -> goldArgb;
            case DEEPSLATE_GOLD     -> deepslateGoldArgb;
            case GRANITE            -> graniteArgb;
            case GRAVEL             -> gravelArgb;
            case INFESTED           -> infestedArgb;
            case DEEPSLATE_INFESTED -> deepslateInfestedArgb;
            case IRON               -> ironArgb;
            case DEEPSLATE_IRON     -> deepslateIronArgb;
            case LAPIS              -> lapisArgb;
            case DEEPSLATE_LAPIS    -> deepslateLapisArgb;
            case REDSTONE           -> redstoneArgb;
            case DEEPSLATE_REDSTONE -> deepslateRedstoneArgb;
            case TUFF               -> tuffArgb;
            case RAW_IRON_BLOCK     -> rawIronBlockArgb;
            case RAW_COPPER_BLOCK   -> rawCopperBlockArgb;

            case ANCIENT_DEBRIS     -> ancientDebrisArgb;
            case BLACKSTONE         -> blackStoneArgb;
            case MAGMA_BLOCK        -> magmaBlockArgb;
            case NETHER_GOLD        -> netherGoldArgb;
            case NETHER_QUARTZ      -> netherQuartzArgb;
            case SOUL_SAND          -> soulSandArgb;
        };
    }

    public boolean getEnabled(OreType type) {
        return switch (type) {
            case ANDESITE           -> andesiteEnabled;
            case CLAY               -> clayEnabled;
            case COAL               -> coalEnabled;
            case DEEPSLATE_COAL     -> deepslateCoalEnabled;
            case COPPER             -> copperEnabled;
            case DEEPSLATE_COPPER   -> deepslateCopperEnabled;
            case DIAMOND            -> diamondEnabled;
            case DEEPSLATE_DIAMOND  -> deepslateDiamondEnabled;
            case DIORITE            -> dioriteEnabled;
            case DIRT               -> dirtEnabled;
            case EMERALD            -> emeraldEnabled;
            case DEEPSLATE_EMERALD  -> deepslateEmeraldEnabled;
            case GOLD               -> goldEnabled;
            case DEEPSLATE_GOLD     -> deepslateGoldEnabled;
            case GRANITE            -> graniteEnabled;
            case GRAVEL             -> gravelEnabled;
            case INFESTED           -> infestedEnabled;
            case DEEPSLATE_INFESTED -> deepslateInfestedEnabled;
            case IRON               -> ironEnabled;
            case DEEPSLATE_IRON     -> deepslateIronEnabled;
            case LAPIS              -> lapisEnabled;
            case DEEPSLATE_LAPIS    -> deepslateLapisEnabled;
            case REDSTONE           -> redstoneEnabled;
            case DEEPSLATE_REDSTONE -> deepslateRedstoneEnabled;
            case TUFF               -> tuffEnabled;
            case RAW_IRON_BLOCK     -> rawIronBlockEnabled;
            case RAW_COPPER_BLOCK   -> rawCopperBlockEnabled;

            case ANCIENT_DEBRIS     -> ancientDebrisEnabled;
            case BLACKSTONE         -> blackStoneEnabled;
            case MAGMA_BLOCK        -> magmaBlockEnabled;
            case NETHER_GOLD        -> netherGoldEnabled;
            case NETHER_QUARTZ      -> netherQuartzEnabled;
            case SOUL_SAND          -> soulSandEnabled;
        };
    }
    
    public void setArgb(OreType type, int value) {
        switch (type) {
            case ANDESITE           -> this.andesiteArgb = value;
            case CLAY               -> this.clayArgb = value;
            case COAL               -> this.coalArgb = value;
            case DEEPSLATE_COAL     -> this.deepslateCoalArgb = value;
            case COPPER             -> this.copperArgb = value;
            case DEEPSLATE_COPPER   -> this.deepslateCopperArgb = value;
            case DIAMOND            -> this.diamondArgb = value;
            case DEEPSLATE_DIAMOND  -> this.deepslateDiamondArgb = value;
            case DIORITE            -> this.dioriteArgb = value;
            case DIRT               -> this.dirtArgb = value;
            case EMERALD            -> this.emeraldArgb = value;
            case DEEPSLATE_EMERALD  -> this.deepslateEmeraldArgb = value;
            case GOLD               -> this.goldArgb = value;
            case DEEPSLATE_GOLD     -> this.deepslateGoldArgb = value;
            case GRANITE            -> this.graniteArgb = value;
            case GRAVEL             -> this.gravelArgb = value;
            case INFESTED           -> this.infestedArgb = value;
            case DEEPSLATE_INFESTED -> this.deepslateInfestedArgb = value;
            case IRON               -> this.ironArgb = value;
            case DEEPSLATE_IRON     -> this.deepslateIronArgb = value;
            case LAPIS              -> this.lapisArgb = value;
            case DEEPSLATE_LAPIS    -> this.deepslateLapisArgb = value;
            case REDSTONE           -> this.redstoneArgb = value;
            case DEEPSLATE_REDSTONE -> this.deepslateRedstoneArgb = value;
            case TUFF               -> this.tuffArgb = value;
            case RAW_IRON_BLOCK     -> this.rawIronBlockArgb = value;
            case RAW_COPPER_BLOCK   -> this.rawCopperBlockArgb = value;

            case ANCIENT_DEBRIS     -> this.ancientDebrisArgb = value;
            case BLACKSTONE         -> this.blackStoneArgb = value;
            case MAGMA_BLOCK        -> this.magmaBlockArgb = value;
            case NETHER_GOLD        -> this.netherGoldArgb = value;
            case NETHER_QUARTZ      -> this.netherQuartzArgb = value;
            case SOUL_SAND          -> this.soulSandArgb = value;
        }
    }
    
    public void setEnabled(OreType type, boolean value) {
        switch (type) {
            case ANDESITE           -> this.andesiteEnabled = value;
            case CLAY               -> this.clayEnabled = value;
            case COAL               -> this.coalEnabled = value;
            case DEEPSLATE_COAL     -> this.deepslateCoalEnabled = value;
            case COPPER             -> this.copperEnabled = value;
            case DEEPSLATE_COPPER   -> this.deepslateCopperEnabled = value;
            case DIAMOND            -> this.diamondEnabled = value;
            case DEEPSLATE_DIAMOND  -> this.deepslateDiamondEnabled = value;
            case DIORITE            -> this.dioriteEnabled = value;
            case DIRT               -> this.dirtEnabled = value;
            case EMERALD            -> this.emeraldEnabled = value;
            case DEEPSLATE_EMERALD  -> this.deepslateEmeraldEnabled = value;
            case GOLD               -> this.goldEnabled = value;
            case DEEPSLATE_GOLD     -> this.deepslateGoldEnabled = value;
            case GRANITE            -> this.graniteEnabled = value;
            case GRAVEL             -> this.gravelEnabled = value;
            case INFESTED           -> this.infestedEnabled = value;
            case DEEPSLATE_INFESTED -> this.deepslateInfestedEnabled = value;
            case IRON               -> this.ironEnabled = value;
            case DEEPSLATE_IRON     -> this.deepslateIronEnabled = value;
            case LAPIS              -> this.lapisEnabled = value;
            case DEEPSLATE_LAPIS    -> this.deepslateLapisEnabled = value;
            case REDSTONE           -> this.redstoneEnabled = value;
            case DEEPSLATE_REDSTONE -> this.deepslateRedstoneEnabled = value;
            case TUFF               -> this.tuffEnabled = value;
            case RAW_IRON_BLOCK     -> this.rawIronBlockEnabled = value;
            case RAW_COPPER_BLOCK   -> this.rawCopperBlockEnabled = value;

            case ANCIENT_DEBRIS     -> this.ancientDebrisEnabled = value;
            case BLACKSTONE         -> this.blackStoneEnabled = value;
            case MAGMA_BLOCK        -> this.magmaBlockEnabled = value;
            case NETHER_GOLD        -> this.netherGoldEnabled = value;
            case NETHER_QUARTZ      -> this.netherQuartzEnabled = value;
            case SOUL_SAND          -> this.soulSandEnabled = value;
        }
    }
    public static final class ConfigConstants {
        public static final int MIN_VERTICAL_VIEW_DISTANCE = -1;
        public static final int MAX_VERTICAL_VIEW_DISTANCE = 10;

        public static final int MIN_LOAD_RADIUS = 0;
        public static final int MAX_LOAD_RADIUS = 9;
    }
}
