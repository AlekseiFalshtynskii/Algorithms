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
public class TreeSearchTest {

    private static final int SIZE = 5_000_000;

    @Param({"random", "order", "dataset"})
    private String test;

    @Param({"avl", "redblack"})
    private String type;

    private AVLTree<Long> longAVLTree;

    private AVLTree<String> stringAVLTree;

    private RedBlackTree<Long> longRedBlackTree;

    private RedBlackTree<String> stringRedBlackTree;

    private long[] numbers = new long[5_000];

    private List<String> words;

    @Setup(Level.Trial)
    public void initTree() {
        if ("avl".equals(type)) {
            initAVLTree();
        } else {
            initRedBlackTree();
        }
    }

    private void initAVLTree() {
        if ("random".equals(test)) {
            longAVLTree = new AVLTree<>();
            for (int i = 0; i < SIZE; i++) {
                longAVLTree.insert(current().nextLong(Long.MAX_VALUE));
            }
        } else if ("order".equals(test)) {
            longAVLTree = new AVLTree<>();
            for (long i = 0; i < SIZE; i++) {
                longAVLTree.insert(i);
            }
            for (int i = 0; i < 5_000; i++) {
                numbers[i] = current().nextLong(Long.MAX_VALUE);
            }
        } else {
            words = parseFile(new File("test/ru/algorithms/lesson10/benchmark/wiki.train.tokens").getAbsolutePath());
            stringAVLTree = new AVLTree<>();
            words.forEach(stringAVLTree::insert);
        }
    }

    private void initRedBlackTree() {
        if ("random".equals(test)) {
            longRedBlackTree = new RedBlackTree<>();
            for (int i = 0; i < SIZE; i++) {
                longRedBlackTree.insert(current().nextLong(Long.MAX_VALUE));
            }
        } else if ("order".equals(test)) {
            longRedBlackTree = new RedBlackTree<>();
            for (long i = 0; i < SIZE; i++) {
                longRedBlackTree.insert(i);
            }
            for (int i = 0; i < 5_000; i++) {
                numbers[i] = current().nextLong(Long.MAX_VALUE);
            }
        } else {
            words = parseFile(new File("test/ru/algorithms/lesson10/benchmark/wiki.train.tokens").getAbsolutePath());
            stringRedBlackTree = new RedBlackTree<>();
            words.forEach(stringRedBlackTree::insert);
        }
    }

    @Benchmark
    public void searchTest() {
        if ("avl".equals(type)) {
            searchAVLTreeTest();
        } else {
            searchRedBlackTreeTest();
        }
    }

    private void searchAVLTreeTest() {
        if ("random".equals(test)) {
            searchRandomAVLTreeTest();
        } else if ("order".equals(test)) {
            searchOrderAVLTreeTest();
        } else {
            searchDataSetAVLTreeTest();
        }
    }

    private void searchRedBlackTreeTest() {
        if ("random".equals(test)) {
            searchRandomRedBlackTreeTest();
        } else if ("order".equals(test)) {
            searchOrderRedBlackTreeTest();
        } else {
            searchDataSetRedBlackTreeTest();
        }
    }

    private void searchRandomAVLTreeTest() {
        for (int i = 0; i < SIZE; i++) {
            longAVLTree.search(current().nextLong(Long.MAX_VALUE));
        }
    }

    private void searchOrderAVLTreeTest() {
        for (int i = 0; i < 1_000; i++) {
            for (long number : numbers) {
                longAVLTree.search(number);
            }
        }
    }

    private void searchDataSetAVLTreeTest() {
        for (int i = 0; i < SIZE; i++) {
            stringAVLTree.search(words.get(current().nextInt(words.size())));
        }
    }

    private void searchRandomRedBlackTreeTest() {
        for (int i = 0; i < SIZE; i++) {
            longRedBlackTree.search(current().nextLong(Long.MAX_VALUE));
        }
    }

    private void searchOrderRedBlackTreeTest() {
        for (int i = 0; i < 1_000; i++) {
            for (long number : numbers) {
                longRedBlackTree.search(number);
            }
        }
    }

    private void searchDataSetRedBlackTreeTest() {
        for (int i = 0; i < SIZE; i++) {
            stringRedBlackTree.search(words.get(current().nextInt(words.size())));
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(TreeSearchTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opts).run();
    }
}
