package ru.algorithms.lesson26;

import java.util.*;

import static java.lang.System.currentTimeMillis;

public class Cache<K, V> {

    private Map<K, Long> times = new LinkedHashMap<>();

    private Map<K, V> values = new HashMap<>();

    private long size = 10;

    private long timeout = 100;

    private Cache() {
    }

    private static class LazyHolder {
        private static final Cache INSTANCE = new Cache();
    }

    public static Cache getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void put(K key, V value) {
        if (values.size() == size) {
            removeFirst();
        }
        times.remove(key);
        times.put(key, currentTimeMillis() + timeout);
        values.put(key, value);
    }

    public V get(K key) {
        removeAllExpired();
        V value = values.get(key);
        if (value != null) {
            times.remove(key);
            times.put(key, currentTimeMillis() + timeout);
        }
        return value;
    }

    public void remove(K key) {
        removeAllExpired();
        times.remove(key);
        values.remove(key);
    }

    private void removeFirst() {
        K key = times.keySet().iterator().next();
        times.remove(key);
        values.remove(key);
    }

    public void clear() {
        times.clear();
        values.clear();
    }

    private void removeAllExpired() {
        final Iterator<Map.Entry<K, Long>> iterator = times.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<K, Long> entry = iterator.next();
            if (isExpired(entry.getValue())) {
                values.remove(entry.getKey());
                iterator.remove();
            } else {
                break;
            }
        }
    }

    private boolean isExpired(Long time) {
        return currentTimeMillis() >= time;
    }
}
