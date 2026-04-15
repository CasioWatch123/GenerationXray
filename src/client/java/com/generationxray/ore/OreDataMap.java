package com.generationxray.ore;

import it.unimi.dsi.fastutil.longs.*;

import java.util.*;

public class OreDataMap {
    private final Map<OreType, LongCollection> map = new EnumMap<>(OreType.class);
    
    public OreDataMap() {
    }
    
    public void put(OreType type, long pos) {
        if (!map.containsKey(type)) {
            map.put(type, new LongArrayList());
        }
        map.get(type).add(pos);
    }
    
    public LongCollection get(OreType type) {
        LongCollection collection = map.get(type);
        
        if (collection != null) {
            return collection;
        }
        return LongArrayList.of();
    }
}
