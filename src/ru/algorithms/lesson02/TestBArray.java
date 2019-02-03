package ru.algorithms.lesson02;

public class TestBArray {
    private static final int ITERATIONS = 15;
    private static final int BLOCK_SIZE = 5;

    public static void main(String[] args) {
        BArray<Integer> b = new BArray<>(BLOCK_SIZE);
        for (int i = 0; i < ITERATIONS; i++) {
            b.add(i, i);
            System.out.println(b);
        }
        b.add(3, 999);
        System.out.println(b);
        b.remove(3);
        System.out.println(b);
    }
}
