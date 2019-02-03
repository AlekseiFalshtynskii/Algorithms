package ru.lesson04.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static ru.lesson04.Sorts.shellSort;
import static ru.lesson04.benchmark.BenchmarkHelper.fillArray;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(value = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BenchmarkShellSortTest {
    private static final int SIZE = 10000;

    @Param({"ordered", "almost ordered", "reversed", "random"})
    private String type;

    @Param({"2", "3", "4"})
    private int k;

    private int[] arr = new int[SIZE];

    @Setup(Level.Trial)
    public void initArray() {
        fillArray(arr, type);
    }

    @Benchmark
    public void shellSortTest() {
        shellSort(arr, k);
    }

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(BenchmarkShellSortTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opts).run();
    }
}
