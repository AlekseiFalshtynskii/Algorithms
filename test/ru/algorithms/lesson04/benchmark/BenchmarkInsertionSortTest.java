package ru.algorithms.lesson04.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

import static ru.algorithms.lesson04.Sorts.insertionSort;
import static ru.algorithms.lesson04.benchmark.BenchmarkHelper.fillArray;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(value = TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BenchmarkInsertionSortTest {

    @Param({"ordered", "almost ordered", "reversed", "random"})
    private String type;

    @Param({"1000", "5000", "10000", "50000", "100000"})
    private int size;

    private int[] arr;

    @Setup(Level.Trial)
    public void initArray() {
        arr = new int[size];
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
