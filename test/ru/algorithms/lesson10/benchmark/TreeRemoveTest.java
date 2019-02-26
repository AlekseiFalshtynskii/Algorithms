package ru.algorithms.lesson10.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ru.algorithms.lesson09.AVLTree;
import ru.algorithms.lesson10.RedBlackTree;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.ThreadLocalRandom.current;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(value = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class TreeRemoveTest {

    private static final int SIZE = 5_000_000;

    @Param({"avl", "redblack"})
    private String type;

    private AVLTree<Long> avlTree;

    private RedBlackTree<Long> redBlackTree;

    @Setup(Level.Trial)
    public void initTree() {
        if ("avl".equals(type)) {
            avlTree = new AVLTree<>();
            for (int i = 0; i < SIZE; i++) {
                avlTree.insert(current().nextLong(Long.MAX_VALUE));
            }
        } else {
            redBlackTree = new RedBlackTree<>();
            for (int i = 0; i < SIZE; i++) {
                redBlackTree.insert(current().nextLong(Long.MAX_VALUE));
            }
        }
    }

    @Benchmark
    public void removeTest() {
        if ("avl".equals(type)) {
            removeAVLTreeTest();
        } else {
            removeRedBlackTreeTest();
        }
    }

    private void removeAVLTreeTest() {
        for (int i = 0; i < 1_000_000; i++) {
            avlTree.remove(current().nextLong(Long.MAX_VALUE));
        }
    }

    private void removeRedBlackTreeTest() {
        for (int i = 0; i < 1_000_000; i++) {
            redBlackTree.remove(current().nextLong(Long.MAX_VALUE));
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(TreeRemoveTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opts).run();
    }
}
