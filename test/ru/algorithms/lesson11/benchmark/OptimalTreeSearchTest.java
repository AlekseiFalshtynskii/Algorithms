package ru.algorithms.lesson11.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import ru.algorithms.lesson11.OptimalTree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.ThreadLocalRandom.current;
import static ru.algorithms.lesson10.benchmark.DatasetImport.parseFile;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(value = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class OptimalTreeSearchTest {

    private static final String DATA_SET = "test/ru/algorithms/lesson10/benchmark/wiki.train.tokens";

    private static final int SIZE = 5_000_000;

    @Param({"1", "2"})
    private int algorithm;

    private List<String> words;

    private OptimalTree<String> tree;

    @Setup(Level.Trial)
    public void initTree() {
        words = parseFile(DATA_SET);

        Map<String, Integer> map = new HashMap<>();
        words.forEach(word -> map.put(word, map.get(word) == null ? 0 : map.get(word) + 1));

        String[] keys = map.keySet().toArray(new String[]{});
        Integer[] freq = map.values().toArray(new Integer[]{});
        tree = new OptimalTree<>();
        if (algorithm == 1) {
            tree.buildTreeByAlgorithm1(keys, freq);
        } else {
            tree.buildTreeByAlgorithm2(keys, freq);
        }
    }

    @Benchmark
    public void searchTest() {
        for (int i = 0; i < SIZE; i++) {
            tree.search(words.get(current().nextInt(words.size())));
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(OptimalTreeSearchTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opts).run();
    }
}
