package ru.algorithms.lesson10.benchmark;

import org.openjdk.jmh.runner.RunnerException;
import ru.algorithms.lesson09.AVLTree;
import ru.algorithms.lesson10.RedBlackTree;

import static java.util.concurrent.ThreadLocalRandom.current;

public class TreeHeightTest {

    private static final int SIZE = 5_000_000;

    private static RedBlackTree<Long> redBlackTree;
    private static AVLTree<Long> avlTree;

    public static void main(String[] args) throws RunnerException {
        redBlackTree = new RedBlackTree<>();
        avlTree = new AVLTree<>();
        for (int i = 0; i < SIZE; i++) {
            long key = current().nextLong(Long.MAX_VALUE);
            redBlackTree.insert(key);
            avlTree.insert(key);
        }
        System.out.println("TreeHeightTest       avl  " + redBlackTree.maxHeight());
        System.out.println("TreeHeightTest  redblack  " + avlTree.maxHeight());
    }
}
