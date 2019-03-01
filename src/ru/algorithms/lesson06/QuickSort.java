package ru.algorithms.lesson06;

import java.util.Arrays;
import java.util.Comparator;

import static java.util.concurrent.ThreadLocalRandom.current;

public class QuickSort {
    private static final int SIZE = 100;

    public static void main(String[] args) {
        Integer[] arr = current().ints(SIZE, 10, 99).boxed().toArray(Integer[]::new);
        quickSort(arr, false);
        System.out.println(Arrays.toString(arr));
    }

    public static <T extends Comparable<T>> void quickSort(T[] arr) {
        quickSort(arr, 0, arr.length - 1, false, null);
    }

    public static <T extends Comparable<T>> void quickSort(T[] arr, boolean withRandomize) {
        quickSort(arr, 0, arr.length - 1, withRandomize, null);
    }

    public static <T extends Comparable<T>> void quickSort(T[] arr, Comparator<T> comparator) {
        quickSort(arr, 0, arr.length - 1, false, comparator);
    }

    static <T extends Comparable<T>> void quickSort(T[] arr, int begin, int end, boolean withRandomize, Comparator<T> comparator) {
        if (begin < end) {
            int pivot = partition(arr, begin, end, withRandomize, comparator);
            quickSort(arr, begin, pivot - 1, withRandomize, comparator);
            quickSort(arr, pivot + 1, end, withRandomize, comparator);
        }
    }

    static <T extends Comparable<T>> int partition(T[] arr, int begin, int end, boolean withRandomize, Comparator<T> comparator) {
        T pivot = arr[withRandomize ? current().nextInt(begin, end) : end];
        int i = begin - 1;
        for (int j = begin; j < end; j++) {
            if (comparator != null) {
                if (comparator.compare(arr[j], pivot) <= 0) {
                    i++;
                    swap(arr, i, j);
                }
            } else if (arr[j].compareTo(pivot) <= 0) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, end);
        return i + 1;
    }

    static <T extends Comparable<T>> void swap(T[] arr, int i, int j) {
        T t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}
