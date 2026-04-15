package com.generationxray.render;

public record OreRenderOption(
        int solidRgba, 
        int outlineRgba, 
        boolean outlineEnabled, 
        boolean solidEnabled
) {
}
