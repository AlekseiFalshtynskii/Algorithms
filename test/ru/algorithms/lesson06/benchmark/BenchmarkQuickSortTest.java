package ru.algorithms.lesson06.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.ThreadLocalRandom.current;
import static ru.algorithms.lesson06.QuickSort.quickSort;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(value = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BenchmarkQuickSortTest {

    private static final int SIZE = 10000;

    @Param({"false", "true"})
    private boolean randomize;

    private int[] arr = new int[SIZE];

    @Setup(Level.Trial)
    public void initArray() {
        arr = current().ints(SIZE, 10, 99).toArray();
    }

    @Benchmark
    public void insertionSortTest() {
        quickSort(arr, randomize);
    }

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(BenchmarkQuickSortTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opts).run();
    }
}
