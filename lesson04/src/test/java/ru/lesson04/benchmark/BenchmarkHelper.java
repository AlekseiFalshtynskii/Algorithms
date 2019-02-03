package ru.lesson04.benchmark;

import java.util.Random;

import static java.lang.Math.random;

class BenchmarkHelper {

    static void fillArray(int[] arr, String type) {
        for (int i = 0; i < arr.length; i++) {
            if ("ordered".equals(type)) {
                arr[i] = i;
            } else if ("almost ordered".equals(type)) {
                if (i == 0) {
                    arr[i] = 1;
                } else {
                    arr[i] = arr[i - 1] + (int) (random() * 12) - 2;
                }
            } else if ("reversed".equals(type)) {
                arr[arr.length - i - 1] = i;
            } else {
                arr[i] = new Random().nextInt(arr.length);
            }
        }
    }
}
