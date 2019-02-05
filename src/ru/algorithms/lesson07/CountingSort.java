package ru.algorithms.lesson07;

import java.util.Arrays;

import static java.lang.System.arraycopy;
import static java.util.concurrent.ThreadLocalRandom.current;

public class CountingSort {
    private static final int SIZE = 100;

    public static void main(String[] args) {
        int[] arr = current().ints(SIZE, 0, 100).toArray();
        System.out.println(Arrays.toString(arr));
        countingSort(arr, 100);
        System.out.println(Arrays.toString(arr));
    }

    static void countingSort(int[] arr, int k) {
        int[] sorted = new int[arr.length];
        int[] counter = new int[k + 1];
        for (int arr_i : arr) {
            counter[arr_i]++;
        }
        for (int i = 1; i < counter.length - 1; i++) {
            counter[i] += counter[i - 1];
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            counter[arr[i]]--;
            sorted[counter[arr[i]]] = arr[i];
        }
        arraycopy(sorted, 0, arr, 0, arr.length);
    }
}
