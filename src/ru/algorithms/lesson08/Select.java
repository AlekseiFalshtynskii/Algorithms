package ru.algorithms.lesson08;


import java.util.Arrays;

import static java.util.Arrays.sort;
import static java.util.concurrent.ThreadLocalRandom.current;

public class Select {
    private static final int SIZE = 15;

    public static void main(String[] args) {
        int[] arr = current().ints(SIZE, 10, 99).toArray();
        System.out.println(Arrays.toString(arr));
        int k = current().nextInt(0, SIZE);
        System.out.println("select(arr, " + k + ") = " + select(arr, k));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    static int select(int[] arr, int k) {
        if (arr.length == 1) {
            return arr[0];
        }
        return select(arr, 0, arr.length - 1, k);
    }

    private static int select(int[] arr, int left, int right, int k) {
        if (left >= right) {
            return arr[left];
        }
        int pivotIndex = pivot(arr, left, right);
        pivotIndex = partition(left, right, arr, pivotIndex);
        if (k <= pivotIndex) {
            return select(arr, left, pivotIndex - 1, k);
        } else if (k == pivotIndex + 1) {
            return arr[pivotIndex];
        } else {
            return select(arr, pivotIndex + 1, right, k);
        }
    }

    private static int partition(int left, int right, int[] arr, int pIndex) {
        swap(arr, pIndex, right);
        int p = arr[right];
        int l = left;
        int r = right - 1;
        while (l <= r) {
            while (l <= r && arr[l] <= p) {
                l++;
            }
            while (l <= r && arr[r] >= p) {
                r--;
            }
            if (l < r) {
                swap(arr, l, r);
            }
        }
        swap(arr, l, right);
        return l;
    }

    private static int pivot(int[] arr, int left, int right) {
        if ((right - left) < 5) {
            return getMedianValue(arr, left, right);
        }
        int count = left;
        for (int i = left; i <= right; i += 5) {
            int tempRight = i + 4;
            if (tempRight > right) {
                tempRight = right;
            }
            int medianSubgroup;
            if ((tempRight - i) <= 2) {
                continue;
            } else {
                medianSubgroup = getMedianValue(arr, i, tempRight);
            }
            swap(arr, medianSubgroup, count);
            count++;
        }
        return pivot(arr, left, count);
    }

    private static int getMedianValue(int[] arr, int left, int right) {
        for (int i = left; i <= right; i++) {
            int j = i;
            while (j > left && arr[j - 1] > arr[j]) {
                swap(arr, j, j - 1);
                j -= 1;
            }
        }
        int medianPos;
        if ((right - left) % 2 == 0) {
            medianPos = ((right - left) / 2) - 1;
        } else {
            medianPos = (right - left) / 2;
        }
        return left + medianPos;
    }

    private static void swap(int[] arr, int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }
}