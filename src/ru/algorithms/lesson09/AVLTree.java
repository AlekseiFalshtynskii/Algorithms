package ru.algorithms.lesson09;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static joptsimple.internal.Strings.repeat;

public class AVLTree {

    private class Node {
        private int height;
        private Integer key, value;
        private Node left, right;

        Node(Integer key, Integer value) {
            this.key = key;
            this.value = value;
            this.left = this.right = null;
            this.height = 1;
        }
    }

    private Node root;

    void insert(Integer key, Integer value) {
        root = insert(root, key, value);
    }

    private Node insert(Node node, Integer key, Integer value) {
        if (node == null) {
            return new Node(key, value);
        }
        int compareResult = key.compareTo(node.key);
        if (compareResult < 0) {
            node.left = insert(node.left, key, value);
            node.height = fixHeight(node);
        } else if (compareResult > 0) {
            node.right = insert(node.right, key, value);
            node.height = fixHeight(node);
        } else {
            node.value = value;
        }
        node = reBalance(node);
        return node;
    }

    private int fixHeight(Node node) {
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int balance(Node node) {
        return height(node.left) - height(node.right);
    }

    private Node reBalance(Node node) {
        int balance = balance(node);
        if (balance == -2) {
            return rotateLeft(node);
        } else if (balance == 2) {
            return rotateRight(node);
        }
        return node;
    }

    private Node rotateLeft(Node node) {
        if (node.right.right == null && node.right.left != null) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
        } else if (node.right.left == null || node.right.left.height <= node.right.right.height) {
            Node newNode = node.right;
            node.right = newNode.left;
            node.height = fixHeight(node);
            newNode.left = node;
            node = newNode;
            node.height = fixHeight(node);
        } else {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
        }
        return node;
    }

    private Node rotateRight(Node node) {
        if (node.left.right != null && node.left.left == null) {
            node.left = rotateLeft(node.left);
            node = rotateRight(node);
        } else if (node.left.right == null || node.left.right.height <= node.left.left.height) {
            Node newNode = node.left;
            node.left = newNode.right;
            node.height = fixHeight(node);
            newNode.right = node;
            node = newNode;
            node.height = fixHeight(node);
        } else {
            node.left = rotateLeft(node.left);
            node = rotateRight(node);
        }
        return node;
    }

    void remove(Integer key) {
        root = remove(root, key);
    }

    private Node remove(Node node, Integer key) {
        if (node == null) {
            return null;
        }
        int compareResult = key.compareTo(node.key);
        if (compareResult < 0) {
            node.left = remove(node.left, key);
        } else if (compareResult > 0) {
            node.right = remove(node.right, key);
        } else {
            if (node.right == null && node.left == null) {
                node = null;
            } else if (node.right == null) {
                node = node.left;
            } else if (node.left == null) {
                node = node.right;
            } else {
                if (node.right.left == null) {
                    node.right.left = node.left;
                    node = node.right;
                } else {
                    Node res = min(node.right);
                    node.key = res.key;
                    node.value = res.value;
                    remove(node.right, node.key);
                }
            }
        }
        if (node != null) {
            node.height = fixHeight(node);
            node = reBalance(node);
        }
        return node;
    }

    private Node min(Node node) {
        return node.left == null ? node : min(node.left);
    }

    void print() {
        int maxHeight = maxHeight();
        printHeight(singletonList(root), 1, maxHeight, 7);
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
                int balance = balance(node);
                String balanceStr = balance == 1 ? "+1" : balance == 0 ? "0 " : "-1";
                System.out.print(node.key + ":" + height(node) + ":" + balanceStr);
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
