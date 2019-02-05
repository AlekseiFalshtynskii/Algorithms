package ru.algorithms.lesson07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.util.Arrays.stream;
import static java.util.concurrent.ThreadLocalRandom.current;

public class TrieBasedRadixSort {
    private static final int SIZE = 10;

    public static void main(String[] args) {
        int[] arr = current().ints(SIZE, 0, 1000).toArray();
        System.out.println(Arrays.toString(arr));
        arr = trieBasedSort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println();
    }

    static int[] trieBasedSort(int[] arr) {
        Trie trie = new Trie(arr);
        int index = current().nextInt(0, arr.length - 1);
        System.out.println("Удален элемент " + arr[index]);
        trie.remove(arr[index]);
        return trie.traverse(arr);
    }

    private static class Trie {
        private TrieNode root;
        private int depth;
        private int size;
        private int sorted_i;

        Trie(int[] arr) {
            size = arr.length;
            depth = (int) log10(stream(arr).max().orElse(0));
            for (int i = 0; i < arr.length; i++) {
                insert(arr[i], i);
            }
        }

        void insert(int n, int index) {
            if (root == null) {
                root = new TrieNode();
            }
            TrieNode currentNode = root;
            int exp = (int) pow(10, depth);
            for (int i = 0; i <= depth; i++) {
                int digit = n / exp;
                if (currentNode.getChild(digit) == null) {
                    currentNode.addChild(digit);
                }
                currentNode = currentNode.getChild(digit);
                n %= exp;
                exp /= 10;
            }
            currentNode.addIndex(index);
        }

        void remove(int n) {
            int exp = (int) pow(10, depth);
            remove(n, root, exp);
            size--;
        }

        private void remove(int n, TrieNode node, int exp) {
            if (node == null || node.getChildrenCount() == 0) {
                return;
            }
            remove(n % exp, node.getChild(n / exp), exp / 10);

            if (exp == 1 || (node.getChild(n / exp) != null && node.getChild(n / exp).isDeleted())) {
                node.removeChild(n / exp);
                if (node.getChildrenCount() == 0) {
                    node.setDeleted();
                }
            }
        }

        int[] traverse(int[] arr) {
            int[] sorted = new int[size];
            sorted_i = 0;
            traverse(root, arr, sorted);
            return sorted;
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
            private int childrenCount;
            private TrieNode[] children;
            private List<Integer> indexes;
            private boolean deleted;

            TrieNode() {
                children = new TrieNode[10];
                indexes = new ArrayList<>();
            }

            TrieNode getChild(int index) {
                return children[index];
            }

            void setDeleted() {
                deleted = true;
            }

            boolean isDeleted() {
                return deleted;
            }

            void removeChild(int index) {
                children[index] = null;
                childrenCount--;
            }

            void addChild(int index) {
                TrieNode node = new TrieNode();
                children[index] = node;
                childrenCount++;
            }

            int getChildrenCount() {
                return childrenCount;
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
