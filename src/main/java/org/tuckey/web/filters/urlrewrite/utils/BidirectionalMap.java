package org.tuckey.web.filters.urlrewrite.utils;

import java.util.HashMap;
import java.util.Map;

public class BidirectionalMap<K, V> {
    private Map<K, V> forwardMap = new HashMap<K, V>();
    private Map<V, K> inverseMap = new HashMap<V, K>();

    public void addPair(K key, V value) {
        forwardMap.put(key, value);
        inverseMap.put(value, key);
    }

    public boolean containsKey(K key) {
        return null != getValue(key);
    }

    public V getValue(K key) {
        return forwardMap.get(key);
    }

    public boolean containsValue(V value) {
        return null != getKey(value);
    }

    public K getKey(V key) {
        return inverseMap.get(key);
    }
}
