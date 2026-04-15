package com.generationxray.world.gen;

import com.generationxray.ore.OreType;
import it.unimi.dsi.fastutil.longs.*;

import java.util.EnumMap;
import java.util.Map;

public class NetherPutConcurrentOreDataMap implements PutConcurrentOreDataMap {
    Map<OreType, LongCollection> oreDataMap = new EnumMap<>(OreType.class);
    Map<OreType, Object> locks = new EnumMap<>(OreType.class);
    
    public NetherPutConcurrentOreDataMap() {
        for(OreType type : OreType.netherValues()) {
            oreDataMap.put(type, new LongArrayList());
            locks.put(type, new Object());
        }
    }


    @Override
    public void put(OreType type, long pos) {
        Object lock = locks.get(type);
        if (lock == null) {
            throw new IllegalArgumentException("wrong ore type key: " + type);
        }
        synchronized (lock) {
            oreDataMap.get(type).add(pos);
        }
    }

    @Override
    public Map<OreType, LongCollection> getSnapshot() {
        Map<OreType, LongCollection> snapshot = new EnumMap<>(OreType.class);
        for(OreType type : OreType.netherValues()) {
            synchronized (locks.get(type)) {
                snapshot.put(type, new LongArrayList(oreDataMap.get(type)));
            }
        }
        return snapshot;
    }

    @Override
    public Map<OreType, LongCollection> getDataMap() {
        return oreDataMap;
    }
}
