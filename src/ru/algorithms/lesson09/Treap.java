package ru.algorithms.lesson09;

import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static joptsimple.internal.Strings.repeat;

public class Treap {

    class Node {
        private int key, priority;
        private Node left, right;

        Node(int key, int priority) {
            this.key = key;
            this.priority = priority;
        }
    }

    private Node root;

    public void insert(int key, int priority) {
        Node temp = root;
        while (temp != null && temp.key != key) {
            if (key < temp.key) {
                temp = temp.left;
            } else {
                temp = temp.right;
            }
        }
        if (temp == null) {
            Node m = new Node(key, priority);
            Pair<Node, Node> p = split(root, key);
            root = merge(p.fst, merge(m, p.snd));
        }
    }

    private Node merge(Node nodeLeft, Node nodeRight) {
        if (nodeRight == null) {
            return nodeLeft;
        } else if (nodeLeft == null) {
            return nodeRight;
        } else if (nodeLeft.priority < nodeRight.priority) {
            nodeRight.left = merge(nodeLeft, nodeRight.left);
            return nodeRight;
        } else {
            nodeLeft.right = merge(nodeLeft.right, nodeRight);
            return nodeLeft;
        }
    }

    @SuppressWarnings("unchecked")
    private Pair<Node, Node> split(Node t, int key) {
        if (t == null) {
            return new Pair(null, null);
        }
        if (t.key < key) {
            Pair<Node, Node> pair = split(t.right, key);
            t.right = null;
            return new Pair(merge(t, pair.fst), pair.snd);
        } else {
            Pair<Node, Node> pair = split(t.left, key);
            t.left = null;
            return new Pair(pair.fst, merge(pair.snd, t));
        }
    }

    void remove(int key) {
        Pair<Node, Node> pair1 = split(root, key - 1);
        Node left = pair1.fst;
        Node right = pair1.snd;
        Pair<Node, Node> pair2 = split(right, key + 1);
        right = pair2.snd;
        root = merge(left, right);
    }

    void print() {
        int maxHeight = maxHeight();
        printHeight(singletonList(root), 1, maxHeight, 5);
        System.out.println();
    }

    private int maxHeight() {
        return maxHeight(root);
    }

    private int maxHeight(Node node) {
        return node == null ? 0 : Math.max(maxHeight(node.left), maxHeight(node.right)) + 1;
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
                System.out.print(node.key + ":" + node.priority);
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