package ru.lesson04;

public class Sorts {

    public static void insertionSort(int arr[]) {
        insertionSort(arr, 0, 1);
    }

    public static void insertionSort(int[] arr, int start, int step) {
        int arr_i;
        for (int i = start; i < arr.length; i += step) {
            arr_i = arr[i];
            for (int j = i; j >= 0; j--) {
                if (j == 0 || arr[j - 1] <= arr_i) {
                    arr[j] = arr_i;
                    break;
                }
                arr[j] = arr[j - 1];
            }
        }
    }

    public static void shellSort(int[] arr, int k) {
        int step = arr.length / k;
        while (step >= 1) {
            for (int i = 0; i < step; i++) {
                insertionSort(arr, i, step);
            }
            step = step / k;
        }
    }
}
