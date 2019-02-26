package ru.algorithms.lesson10.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ru.algorithms.lesson09.AVLTree;
import ru.algorithms.lesson10.RedBlackTree;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.ThreadLocalRandom.current;
import static ru.algorithms.lesson10.benchmark.DatasetImport.parseFile;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(value = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class TreeInsertTest {

    private static final int SIZE = 5_000_000;

    @Param({"random", "order", "dataset"})
    private String test;

    @Param({"avl", "redblack"})
    private String type;

    private List<String> words;

    @Setup(Level.Trial)
    public void initArray() {
        if ("dataset".equals(test)) {
            words = parseFile(new File("test/ru/algorithms/lesson10/benchmark/wiki.train.tokens").getAbsolutePath());
        }
    }

    @Benchmark
    public void insertTest() {
        if ("avl".equals(type)) {
            if ("random".equals(test)) {
                insertRandomAVLTreeTest(new AVLTree<>());
            } else if ("order".equals(test)) {
                insertOrderAVLTreeTest(new AVLTree<>());
            } else {
                insertDataSetAVLTreeTest(new AVLTree<>());
            }
        } else {
            if ("random".equals(test)) {
                insertRandomRedBlackTreeTest(new RedBlackTree<>());
            } else if ("order".equals(test)) {
                insertOrderRedBlackTreeTest(new RedBlackTree<>());
            } else {
                insertDataSetRedBlackTreeTest(new RedBlackTree<>());
            }
        }
    }

    private void insertRandomAVLTreeTest(AVLTree<Long> tree) {
        for (int i = 0; i < SIZE; i++) {
            tree.insert(current().nextLong(Long.MAX_VALUE));
        }
    }

    private void insertOrderAVLTreeTest(AVLTree<Long> tree) {
        for (long i = 0; i < SIZE; i++) {
            tree.insert(i);
        }
    }

    private void insertDataSetAVLTreeTest(AVLTree<String> tree) {
        words.forEach(tree::insert);
    }

    private void insertRandomRedBlackTreeTest(RedBlackTree<Long> tree) {
        for (int i = 0; i < SIZE; i++) {
            tree.insert(current().nextLong(Long.MAX_VALUE));
        }
    }

    private void insertOrderRedBlackTreeTest(RedBlackTree<Long> tree) {
        for (long i = 0; i < SIZE; i++) {
            tree.insert(i);
        }
    }

    private void insertDataSetRedBlackTreeTest(RedBlackTree<String> tree) {
        words.forEach(tree::insert);
    }

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(TreeInsertTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opts).run();
    }
}
