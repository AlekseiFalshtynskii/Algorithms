package ru.lesson04.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static ru.lesson04.Sorts.insertionSort;
import static ru.lesson04.benchmark.BenchmarkHelper.fillArray;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(value = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BenchmarkInsertionSortTest {
    private static final int SIZE = 10000;

    @Param({"ordered", "almost ordered", "reversed", "random"})
    private String type;

    private int[] arr = new int[SIZE];

    @Setup(Level.Trial)
    public void initArray() {
        fillArray(arr, type);
    }

    @Benchmark
    public void insertionSortTest() {
        insertionSort(arr);
    }

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(BenchmarkInsertionSortTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opts).run();
    }
}
