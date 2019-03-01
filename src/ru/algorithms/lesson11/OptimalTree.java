package ru.algorithms.lesson11;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.util.Collections.singletonList;
import static java.util.Comparator.comparing;
import static joptsimple.internal.Strings.repeat;
import static ru.algorithms.lesson06.QuickSort.quickSort;

public class OptimalTree<Key extends Comparable<Key>> {

    class Node {
        Key key;
        Integer freq;
        Node left, right;

        Node(Key key, Integer freq) {
            this.key = key;
            this.freq = freq;
        }
    }

    Node root;

    /**
     * Построение дерева по Алгоритму 1
     *
     * @param keys
     * @param freq
     */
    public void buildTreeByAlgorithm1(Key[] keys, Integer[] freq) {
        DataNode<Key>[] dataNodes = new DataNode[keys.length];
        for (int i = 0; i < keys.length; i++) {
            dataNodes[i] = new DataNode(keys[i], freq[i]);
        }

        // Сортировка по частоте freq
        quickSort(dataNodes, comparing(o -> o.freq));

        for (int i = dataNodes.length - 1; i >= 0; i--) {
            insert(dataNodes[i].key, dataNodes[i].freq);
        }
    }

    /**
     * Построение дерева по Алгоритму 2
     *
     * @param keys
     * @param freq
     */
    public void buildTreeByAlgorithm2(Key[] keys, Integer[] freq) {
        DataNode<Key>[] dataNodes = new DataNode[keys.length];
        for (int i = 0; i < keys.length; i++) {
            dataNodes[i] = new DataNode(keys[i], freq[i]);
        }

        // Сортировка по ключу key
        quickSort(dataNodes);

        buildTreeByAlgorithm2(dataNodes, 0, dataNodes.length);
    }

    private void buildTreeByAlgorithm2(DataNode<Key>[] dataNodes, int left, int right) {
        if (right <= left) {
            return;
        }
        int index = indexOptimalNode(dataNodes, left, right);
        insert(dataNodes[index].key, dataNodes[index].freq);
        buildTreeByAlgorithm2(dataNodes, left, index);
        buildTreeByAlgorithm2(dataNodes, index + 1, right);
    }

    private int indexOptimalNode(DataNode<Key>[] dataNodes, int left, int right) {
        int diffMin = Integer.MAX_VALUE;
        int index = 0;
        for (int i = left; i < right; i++) {
            int sumLeft = sum(dataNodes, left, i);
            int sumRight = sum(dataNodes, i + 1, right);
            int diff = abs(sumLeft - sumRight);
            if (diff < diffMin) {
                diffMin = diff;
                index = i;
            }
        }
        return index;
    }

    private int sum(DataNode<Key>[] dataNodes, int left, int right) {
        int sum = 0;
        for (int l = left; l < right; l++) {
            sum += dataNodes[l].freq;
        }
        return sum;
    }

    /**
     * Вставка в дерево
     *
     * @param key
     * @param freq
     */
    private void insert(Key key, Integer freq) {
        Node node = new Node(key, freq);
        if (root == null) {
            root = node;
        } else {
            Node temp = root;

            /* Ищем узел, к которому присоединить новый */
            while (true) {
                if (key.compareTo(temp.key) < 0) {
                    if (temp.left == null) {
                        break;
                    }
                    temp = temp.left;
                } else if (key.compareTo(temp.key) > 0) {
                    if (temp.right == null) {
                        break;
                    }
                    temp = temp.right;
                } else {
                    return;
                }
            }
            if (key.compareTo(temp.key) < 0) {
                temp.left = node;
            } else {
                temp.right = node;
            }
        }
    }

    /**
     * Поиск по дереву
     *
     * @param key
     * @return
     */
    public Node search(Key key) {
        return search(key, root);
    }

    private Node search(Key key, Node node) {
        if (node == null || key.compareTo(node.key) == 0) {
            return node;
        }
        if (key.compareTo(node.key) < 0) {
            return search(key, node.left);
        }
        return search(key, node.right);
    }

    public void print() {
        int maxHeight = maxHeight();
        printHeight(singletonList(root), 1, maxHeight, 5);
        System.out.println();
    }

    private int maxHeight() {
        return maxHeight(root);
    }

    private int maxHeight(Node node) {
        return node == null ? 0 : max(maxHeight(node.left), maxHeight(node.right)) + 1;
    }

    private void printHeight(List<Node> nodes, int height, int maxHeight, int length) {
        if (nodes.isEmpty() || isNullAllElements(nodes)) {
            return;
        }
        printTabs((int) (Math.pow(2, maxHeight - height + 1) - 1) / 2, length);
        List<Node> newNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node == null) {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(repeat(' ', length));
            } else {
                System.out.print(node.key + ":" + node.freq);
                newNodes.add(node.left);
                newNodes.add(node.right);
            }
            printTabs((int) (Math.pow(2, maxHeight - height + 2) - 1) / 2, length);
        }
        System.out.println();
        printHeight(newNodes, height + 1, maxHeight, length);
    }

    private boolean isNullAllElements(List<Node> list) {
        for (Object object : list) {
            if (object != null) {
                return false;
            }
        }
        return true;
    }

    private void printTabs(int tabs, int length) {
        for (int t = 0; t < tabs; t++) {
            System.out.print(repeat(' ', length));
        }
    }
}
