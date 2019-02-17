package ru.algorithms.lesson05;

import static java.util.concurrent.ThreadLocalRandom.current;

public class HeapSort {
    private static final int N = 31;

    public static void main(String[] args) {
        Heap heap = new Heap(current().ints(N, 10, 99).toArray());

        System.out.println("Построенная пирамида:");
        heap.print();

        int index = current().nextInt(0, heap.size() - 1);
        heap.remove(index);
        System.out.println("Пирамида после удаления элемента " + index + ":");
        heap.print();

        heap.sort();

        System.out.println("Отсортированный массив:");
        heap.printArr();
    }
}
