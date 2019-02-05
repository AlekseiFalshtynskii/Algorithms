package ru.algorithms.lesson07;

import java.util.Arrays;

import static java.lang.System.arraycopy;
import static java.util.Arrays.stream;
import static java.util.concurrent.ThreadLocalRandom.current;

public class RadixSort {
    private static final int SIZE = 100;

    public static void main(String[] args) {
        int[] arr = current().ints(SIZE, 0, 99).toArray();
        System.out.println(Arrays.toString(arr));
        int max = stream(arr).max().orElse(0);
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(arr, exp);
        }
        System.out.println(Arrays.toString(arr));
    }

    static void countSort(int arr[], int exp) {
        int sorted[] = new int[arr.length];
        int count[] = new int[10];
        for (int arr_i : arr) {
            count[(arr_i / exp) % 10]++;
        }
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        for (int i = arr.length - 1; i >= 0; i--) {
            sorted[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }
        arraycopy(sorted, 0, arr, 0, arr.length);
    }
}
