package ru.algorithms.lesson06;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import static java.util.concurrent.ThreadLocalRandom.current;

public class QuickSortForkJoin {
    private static final int SIZE = 100;

    public static void main(String[] args) {
        int[] arr = current().ints(SIZE, 10, 99).toArray();
        quickSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    static void quickSort(int[] arr) {
        QuickSortAction quickSort = new QuickSortAction(arr);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(quickSort);
        pool.shutdown();
    }

    private static class QuickSortAction extends RecursiveAction {
        private int[] arr;
        private int begin;
        private int end;

        QuickSortAction(int[] arr) {
            this.arr = arr;
            this.begin = 0;
            this.end = arr.length - 1;
        }

        QuickSortAction(int[] arr, int begin, int end) {
            this.arr = arr;
            this.begin = begin;
            this.end = end;
        }

        @Override
        protected void compute() {
            if (begin < end) {
                int pivot = partition(arr, begin, end);
                invokeAll(
                        new QuickSortAction(arr, begin, pivot - 1),
                        new QuickSortAction(arr, pivot + 1, end)
                );
            }
        }

        int partition(int[] arr, int begin, int end) {
            int pivot = arr[end];
            int i = begin - 1;
            for (int j = begin; j < end; j++) {
                if (arr[j] <= pivot) {
                    i++;
                    swap(arr, i, j);
                }
            }
            swap(arr, i + 1, end);
            return i + 1;
        }

        void swap(int[] arr, int i, int j) {
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }
    }
}
