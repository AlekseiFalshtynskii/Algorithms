package ru.algorithms.lesson06;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import static java.util.concurrent.ThreadLocalRandom.current;

public class MergeSortForkJoin {
    private static final int SIZE = 100;

    public static void main(String[] args) {
        int[] arr = current().ints(SIZE, 10, 99).toArray();
        mergeSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    static void mergeSort(int[] arr) {
        MergeSortAction mergeSortAction = new MergeSortAction(arr);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(mergeSortAction);
        pool.shutdown();
    }

    private static class MergeSortAction extends RecursiveAction {
        private int[] arr;
        private int[] copy;
        private int begin;
        private int end;

        MergeSortAction(int[] arr) {
            this.arr = arr;
            this.copy = Arrays.copyOf(arr, arr.length);
            this.begin = 0;
            this.end = arr.length - 1;
        }

        MergeSortAction(int[] arr, int begin, int end, int[] copy) {
            this.arr = copy;
            this.begin = begin;
            this.end = end;
            this.copy = arr;
        }

        @Override
        protected void compute() {
            if (begin < end) {
                int middle = (begin + end) / 2;
                invokeAll(
                        new MergeSortAction(arr, begin, middle, copy),
                        new MergeSortAction(arr, middle + 1, end, copy)
                );
                merge(copy, begin, middle, end, arr);
            }
        }

        private void merge(int[] arr, int begin, int middle, int end, int[] copy) {
            int first = begin;
            int second = middle + 1;
            for (int i = begin; i <= end; i++) {
                if (first <= middle && (second > end || arr[first] <= arr[second])) {
                    copy[i] = arr[first];
                    first++;
                } else {
                    copy[i] = arr[second];
                    second++;
                }
            }
        }
    }
}
