package ru.algorithms.lesson06;

import java.util.Arrays;

import static java.util.concurrent.ThreadLocalRandom.current;

public class QuickSort {
    private static final int SIZE = 100;

    public static void main(String[] args) {
        int[] arr = current().ints(SIZE, 10, 99).toArray();
        quickSort(arr, false);
        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr, boolean withRandomize) {
        quickSort(arr, 0, arr.length - 1, withRandomize);
    }

    static void quickSort(int[] arr, int begin, int end, boolean withRandomize) {
        if (begin < end) {
            int pivot = partition(arr, begin, end, withRandomize);
            quickSort(arr, begin, pivot - 1, withRandomize);
            quickSort(arr, pivot + 1, end, withRandomize);
        }
    }

    static int partition(int[] arr, int begin, int end, boolean withRandomize) {
        int pivot = arr[withRandomize ? current().nextInt(begin, end) : end];
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

    static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}
