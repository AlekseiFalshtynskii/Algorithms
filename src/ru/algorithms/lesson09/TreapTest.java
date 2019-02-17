package ru.algorithms.lesson09;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

public class TreapTest {
    private static final int SIZE = 5;

    public static void main(String[] args) {
        List<Integer> keys = new ArrayList<>(SIZE);
        int key;
        int priority;
        int value;
        Treap treap = new Treap();
        for (int i = 0; i < SIZE; i++) {
            key = current().nextInt(10, 99);
            priority = current().nextInt(10, 99);
            value = current().nextInt(10, 99);
            keys.add(key);
            treap.insert(key, priority, value);
            System.out.println("Inserted key: " + key + ", priority: " + priority + ", value: " + value);
            treap.print();
        }
        key = keys.get(current().nextInt(keys.size()));
        treap.remove(key);
        System.out.println("Removed key: " + key);
        treap.print();
    }
}
