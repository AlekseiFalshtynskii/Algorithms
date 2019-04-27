package ru.algorithms.lesson26;

import java.util.concurrent.TimeUnit;

import static ru.algorithms.lesson26.Cache.getInstance;

public class Test {

    static Cache<Long, Long> cache = getInstance();

    public static void main(String[] args) throws InterruptedException {
        cache.put(1L, 1L);
        cache.put(2L, 2L);
        cache.put(3L, 3L);
        cache.put(4L, 4L);
        cache.put(5L, 5L);
        cache.put(6L, 6L);
        cache.put(7L, 7L);
        cache.put(8L, 8L);
        cache.put(9L, 9L);
        cache.put(10L, 10L);
        cache.put(11L, 11L);
        System.out.println(cache.get(1L) == null);

        TimeUnit.MILLISECONDS.sleep(101);
        System.out.println(cache.get(5L) == null);

        cache.clear();
        cache.put(100L, 100L);
        TimeUnit.MILLISECONDS.sleep(90);
        System.out.println(cache.get(100L) == 100L);

        TimeUnit.MILLISECONDS.sleep(101);
        System.out.println(cache.get(100L) == null);
    }
}
