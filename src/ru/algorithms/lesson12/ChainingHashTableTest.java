package ru.algorithms.lesson12;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

public class ChainingHashTableTest {
    private static final int SIZE = 100;

    public static void main(String[] args) {
        ChainingHashTable hashTable = new ChainingHashTable();
        List<Integer> keys = new ArrayList<>();
        int key;
        int value;
        for (int i = 0; i < SIZE; i++) {
            key = current().nextInt(0, 1_000);
            value = current().nextInt(0, 1_000);
            hashTable.add(key, value);
            keys.add(key);
        }
        hashTable.remove(keys.get(0));
    }
}
