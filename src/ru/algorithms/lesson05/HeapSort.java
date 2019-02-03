package ru.algorithms.lesson05;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.concurrent.ThreadLocalRandom.current;

public class HeapSort {
    private static final int N = 31;
    private static int heapSize;

    public static void main(String[] args) {
        int[] heap = current().ints(N, 10, 99).toArray();

        buildHeap(heap);
        System.out.println("Построенная пирамида:");
        printHeap(heap);

        int deleted = current().nextInt(0, heap.length - 1);
        heap = deleteFromHeap(heap, deleted);
        System.out.println("Пирамида после удаления элемента " + deleted + ":");
        printHeap(heap);

        sort(heap);

        System.out.println("Отсортированный массив:");
        System.out.println(Arrays.toString(heap));
    }

    static void buildHeap(int[] heap) {
        heapSize = heap.length;
        for (int i = heap.length / 2; i >= 0; i--) {
            drown(heap, i);
        }
    }

    static void sort(int[] heap) {
        while (heapSize > 1) {
            swap(heap, 0, heapSize - 1);
            heapSize--;
            drown(heap, 0);
        }
    }

    static void drown(int[] heap, int i) {
        int l = left(i);
        int r = right(i);
        int largest = i;
        if (l < heapSize && heap[i] < heap[l]) {
            largest = l;
        }
        if (r < heapSize && heap[largest] < heap[r]) {
            largest = r;
        }
        if (i != largest) {
            swap(heap, i, largest);
            drown(heap, largest);
        }
    }

    static int left(int i) {
        return 2 * i + 1;
    }

    static int right(int i) {
        return 2 * i + 2;
    }

    static void swap(int[] heap, int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    static int[] deleteFromHeap(int[] heap, int i) {
        swap(heap, i, heap.length - 1);
        int[] new_heap = new int[heap.length - 1];
        System.arraycopy(heap, 0, new_heap, 0, new_heap.length);
        heapSize--;
        drown(new_heap, i);
        return new_heap;
    }

    static void printHeap(int[] heap) {
        int height = log2(heap.length) + 1;
        boolean new_line = true;

        for (int i = 1, len = heap.length; i <= len; i++) {
            int x = heap[i - 1];
            int level = log2(i) + 1;
            if (new_line) {
                printTabs((int) (Math.pow(2, height - level + 1) - 1) / 2);
                new_line = false;
            }
            System.out.print(x);
            if (level != 1) {
                printTabs((int) (Math.pow(2, height - level + 2) - 1) / 2);
            }

            if ((int) Math.pow(2, level) - 1 == i) {
                System.out.println();
                new_line = true;
            }
        }
        System.out.println();
        System.out.println();
    }

    static void printTabs(int tabs) {
        for (int t = 0; t < tabs; t++) {
            System.out.print("  ");
        }
    }

    static int log2(int x) {
        return (int) (Math.log(x) / Math.log(2));
    }
}
