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

import static ru.algorithms.lesson10.benchmark.DatasetImport.parseFile;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(value = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class OptimalTreeBuildTest {

    private static final String DATA_SET = "test/ru/algorithms/lesson10/benchmark/wiki.train.tokens";

    @Param({"1", "2"})
    private int algorithm;

    private String[] keys;
    private Integer[] freq;

    @Setup(Level.Trial)
    public void initData() {
        List<String> words = parseFile(DATA_SET);

        Map<String, Integer> map = new HashMap<>();
        words.forEach(word -> map.put(word, map.get(word) == null ? 0 : map.get(word) + 1));

        keys = map.keySet().toArray(new String[]{});
        freq = map.values().toArray(new Integer[]{});
    }

    @Benchmark
    public void buildTest() {
        OptimalTree<String> tree = new OptimalTree<>();
        if (algorithm == 1) {
            tree.buildTreeByAlgorithm1(keys, freq);
        } else {
            tree.buildTreeByAlgorithm2(keys, freq);
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(OptimalTreeBuildTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opts).run();
    }
}
