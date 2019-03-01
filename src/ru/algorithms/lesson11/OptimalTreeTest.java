package ru.algorithms.lesson11;

import ru.algorithms.lesson09.Treap;

import static java.util.concurrent.ThreadLocalRandom.current;

public class OptimalTreeTest {

    private static final int SIZE = 10;

    public static void main(String[] args) {
        Integer[] keys = current().ints(SIZE, 10, 99).boxed().toArray(Integer[]::new);
        Integer[] freq = current().ints(SIZE, 10, 99).boxed().toArray(Integer[]::new);

        OptimalTree<Integer> tree = new OptimalTree<>();
        tree.buildTreeByAlgorithm1(keys, freq);

        System.out.println("Дерево, построенное по Алгоритму 1");
        tree.print();

        tree = new OptimalTree<>();
        tree.buildTreeByAlgorithm2(keys, freq);

        System.out.println("Дерево, построенное по Алгоритму 2");
        tree.print();

        Treap treap = new Treap();
        for (int i = 0; i < keys.length; i++) {
            treap.insert(keys[i], freq[i]);
        }
        System.out.println("Treap");
        tree.print();
    }
}
