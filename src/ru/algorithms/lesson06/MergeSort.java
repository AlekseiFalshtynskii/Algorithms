package ru.algorithms.lesson06;

import java.util.Arrays;

import static java.util.concurrent.ThreadLocalRandom.current;

public class MergeSort {
    private static final int SIZE = 100;

    public static void main(String[] args) {
        int[] arr = current().ints(SIZE, 10, 99).toArray();
        mergeSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    static void mergeSort(int[] arr) {
        int[] copy = Arrays.copyOf(arr, arr.length);
        splitMerge(copy, 0, arr.length - 1, arr);
    }

    static void splitMerge(int[] copy, int begin, int end, int[] arr) {
        if (begin < end) {
            int middle = (begin + end) / 2;
            splitMerge(arr, begin, middle, copy);
            splitMerge(arr, middle + 1, end, copy);
            merge(copy, begin, middle, end, arr);
        }
    }

    static void merge(int[] arr, int begin, int middle, int end, int[] copy) {
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
