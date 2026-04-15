package com.generationxray.world.gen;

import com.generationxray.ore.OreType;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.longs.LongSet;

import java.util.Map;

public interface PutConcurrentOreDataMap {
    void put(OreType type, long pos);
    
    Map<OreType, LongCollection> getSnapshot();
    
    Map<OreType, LongCollection> getDataMap();
}
