package ru.algorithms.lesson10;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

public class RedBlackTreeTest {
    private static final int SIZE = 10;

    public static void main(String[] args) {
        List<Integer> keys = new ArrayList<>(SIZE);
        int key;
        RedBlackTree<Integer> tree = new RedBlackTree();
        for (int i = 0; i < SIZE; i++) {
            key = current().nextInt(10, 99);
            keys.add(key);
            tree.insert(key);
            System.out.println("Inserted key: " + key);
            tree.print();
        }
        key = keys.get(current().nextInt(keys.size()));
        System.out.println("Removed key: " + key);
        tree.remove(key);
        tree.print();
    }
}
