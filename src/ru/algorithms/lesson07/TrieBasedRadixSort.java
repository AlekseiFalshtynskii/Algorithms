package ru.algorithms.lesson07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.System.arraycopy;
import static java.util.Arrays.stream;
import static java.util.concurrent.ThreadLocalRandom.current;

public class TrieBasedRadixSort {
    private static final int SIZE = 100;

    public static void main(String[] args) {
        int[] arr = current().ints(SIZE, 0, 1000).toArray();
        System.out.println(Arrays.toString(arr));
        trieBasedSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    static void trieBasedSort(int[] arr) {
        Trie trie = new Trie(arr);
        trie.traverse(arr);
    }

    private static class Trie {
        private TrieNode root;
        private int depth;
        private int sorted_i;

        Trie(int[] arr) {
            depth = (int) log10(stream(arr).max().orElse(0));
            for (int i = 0; i < arr.length; i++) {
                insert(arr[i], i);
            }
        }

        private void insert(int n, int index) {
            if (root == null) {
                root = new TrieNode();
            }
            TrieNode currentNode = root;
            int exp = (int) pow(10, depth);
            for (int i = 0; i <= depth; i++) {
                int digit = n / exp;
                if (currentNode.children == null || currentNode.getChild(digit) == null) {
                    currentNode.addChild(digit);
                }
                currentNode = currentNode.getChild(digit);
                n %= exp;
                exp /= 10;
            }
            currentNode.addIndex(index);
        }

        void traverse(int[] arr) {
            int[] sorted = new int[arr.length];
            sorted_i = 0;
            traverse(root, arr, sorted);
            arraycopy(sorted, 0, arr, 0, arr.length);
        }

        private void traverse(TrieNode node, int[] arr, int[] sorted) {
            if (node == null) {
                return;
            }
            if (node.getIndexes().size() > 0) {
                for (int index : node.getIndexes()) {
                    sorted[sorted_i++] = arr[index];
                }
            }
            for (int i = 0; i < 10; i++) {
                traverse(node.getChild(i), arr, sorted);
            }
        }

        private static class TrieNode {
            private TrieNode[] children;
            private List<Integer> indexes;

            TrieNode() {
                children = new TrieNode[10];
                indexes = new ArrayList<>();
            }

            TrieNode getChild(int index) {
                return children[index];
            }

            void addChild(int index) {
                TrieNode node = new TrieNode();
                children[index] = node;
            }

            List<Integer> getIndexes() {
                return indexes;
            }

            void addIndex(int index) {
                indexes.add(index);
            }
        }
    }
}
