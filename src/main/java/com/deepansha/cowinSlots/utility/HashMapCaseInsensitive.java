package com.deepansha.cowinSlots.utility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashMapCaseInsensitive <K, V> extends HashMap<String, V> {

    public HashMapCaseInsensitive(Map<String, V> map) {
        Set<Map.Entry<String, V>> set = ((Map<String, V>) map).entrySet();
        Iterator<Map.Entry<String, V>> itr = set.iterator();
        while (itr.hasNext()) {
            Map.Entry<String, V> entry = itr.next();
            this.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public V put(String key, V value) {
        return super.put(key.toString().toLowerCase(), value);
    }

    @Override
    public V get(Object key) {
        return super.get(key.toString().toLowerCase());
    }
}
