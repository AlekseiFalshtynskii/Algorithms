package ru.algorithms.lesson05;

public class Heap {
    private int heapSize;
    private int heapSizeSort;
    private int[] heap;

    Heap(int[] arr) {
        heap = arr;
        buildHeap(heap);
    }

    int size() {
        return heapSize;
    }

    private void buildHeap(int[] heap) {
        heapSize = heapSizeSort = heap.length;
        for (int i = heapSize / 2; i >= 0; i--) {
            drown(heap, i);
        }
    }

    void sort() {
        while (heapSizeSort > 1) {
            swap(heap, 0, heapSizeSort - 1);
            heapSizeSort--;
            drown(heap, 0);
        }
    }

    private void drown(int[] heap, int i) {
        int l = left(i);
        int r = right(i);
        int largest = i;
        if (l < heapSizeSort && heap[i] < heap[l]) {
            largest = l;
        }
        if (r < heapSizeSort && heap[largest] < heap[r]) {
            largest = r;
        }
        if (i != largest) {
            swap(heap, i, largest);
            drown(heap, largest);
        }
    }

    private int left(int i) {
        return 2 * i + 1;
    }

    private int right(int i) {
        return 2 * i + 2;
    }

    private void swap(int[] heap, int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    void remove(int i) {
        swap(heap, i, heapSize - 1);
        heapSize--;
        drown(heap, i);
    }

    void print() {
        int height = log2(heapSize) + 1;
        boolean new_line = true;

        for (int i = 1, len = heapSize; i <= len; i++) {
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

    private void printTabs(int tabs) {
        for (int t = 0; t < tabs; t++) {
            System.out.print("  ");
        }
    }

    private int log2(int x) {
        return (int) (Math.log(x) / Math.log(2));
    }

    void printArr() {
        System.out.print("[");
        for (int i = 0; i < heapSize; i++) {
            System.out.print(heap[i] + (i < heapSize - 1 ? ", " : ""));
        }
        System.out.print("]");
        System.out.println();
    }
}
