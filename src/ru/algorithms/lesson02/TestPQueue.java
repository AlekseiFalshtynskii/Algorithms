package ru.algorithms.lesson02;

public class TestPQueue {

    public static void main(String[] args) {
        PQueue<Integer> pQueue = new PQueue<>();
        pQueue.enqueue(3, 1);
        System.out.println(pQueue);
        pQueue.enqueue(1, 1);
        System.out.println(pQueue);
        pQueue.enqueue(2, 1);
        System.out.println(pQueue);
        for (int i = 2; i < 7; i++) {
            pQueue.enqueue(3, i);
            System.out.println(pQueue);
        }
        for (int i = 0; i < 9; i++) {
            System.out.println("dequeue == " + pQueue.dequeue());
            System.out.println(pQueue);
        }
    }
}
