package ru.algorithms.lesson09;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

public class AVLTreeTest {
    private static final int SIZE = 13;

    public static void main(String[] args) {
        List<Integer> keys = new ArrayList<>(SIZE);
        int key;
        AVLTree<Integer> tree = new AVLTree();
        for (int i = 0; i < SIZE; i++) {
            key = current().nextInt(10, 99);
            keys.add(key);
            tree.insert(key);
            System.out.println("Inserted key: " + key);
            tree.print();
        }
        key = keys.get(current().nextInt(keys.size()));
        tree.remove(key);
        System.out.println("Removed key: " + key);
        tree.print();
    }
}
