package ru.algorithms.lesson10;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static joptsimple.internal.Strings.repeat;

public class RedBlackTree<Key extends Comparable<Key>> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private class Node {
        private Key key;
        private Node left, right, parent;
        private boolean color = RED;

        Node(Key key) {
            this.key = key;
        }

        @Override
        public String toString() {
            return key + ":" + (color == BLACK ? "B" : "R");
        }
    }

    private Node root;

    /**
     * Вставка нового узла в дерево
     *
     * @param key
     */
    public void insert(Key key) {
        Node newNode = new Node(key);
        if (root == null) {
            root = newNode;
        } else {
            Node temp = root;
            Node node = null;

            /* Ищем узел, к которому присоединить новый */
            while (temp != null) {
                node = temp;
                if (key.compareTo(temp.key) < 0) {
                    temp = temp.left;
                } else if (key.compareTo(temp.key) > 0) {
                    temp = temp.right;
                } else {
                    return;
                }
            }
            newNode.parent = node;
            if (key.compareTo(node.key) < 0) {
                node.left = newNode;
            } else {
                node.right = newNode;
            }
        }
        insertFixUp(newNode);
    }

    private void insertFixUp(Node node) {
        while (node != null && node != root && node.parent.color != BLACK) {
            Node grandParent = getGrandParent(node);
            if (isLeft(node.parent)) {
                Node right = grandParent.right;
                if (right != null && right.color == RED) {
                    node.parent.color = BLACK;
                    right.color = BLACK;
                    grandParent.color = RED;
                    node = grandParent;
                } else {
                    if (isRight(node)) {
                        node = node.parent;
                        rotateLeft(node);
                    }
                    node.parent.color = BLACK;
                    grandParent.color = RED;
                    rotateRight(grandParent);
                }
            } else {
                Node left = grandParent.left;
                if (left != null && left.color == RED) {
                    node.parent.color = BLACK;
                    left.color = BLACK;
                    grandParent.color = RED;
                    node = grandParent;
                } else {
                    if (isLeft(node)) {
                        node = node.parent;
                        rotateRight(node);
                    }
                    node.parent.color = BLACK;
                    grandParent.color = RED;
                    rotateLeft(grandParent);
                }
            }
        }
        root.color = BLACK;
    }

    /**
     * Поворот налево
     *
     * @param node
     */
    private void rotateLeft(Node node) {
        if (node != null) {
            Node pivot = node.right;
            node.right = pivot.left;
            if (pivot.left != null) {
                pivot.left.parent = node;
            }
            pivot.parent = node.parent;

            if (node.parent == null) {
                root = pivot;
            } else {
                if (node.parent.left == node) {
                    node.parent.left = pivot;
                } else {
                    node.parent.right = pivot;
                }
            }
            pivot.left = node;
            node.parent = pivot;
        }
    }

    /**
     * Поворот направо
     *
     * @param node
     */
    private void rotateRight(Node node) {
        if (node != null) {
            Node pivot = node.left;
            node.left = pivot.right;
            if (pivot.right != null)
                pivot.right.parent = node;
            pivot.parent = node.parent;

            if (node.parent == null) {
                root = pivot;
            } else {
                if (node == node.parent.right)
                    node.parent.right = pivot;
                else
                    node.parent.left = pivot;
            }
            pivot.right = node;
            node.parent = pivot;
        }
    }

    private boolean isLeft(Node node) {
        return node == node.parent.left;
    }

    private boolean isRight(Node node) {
        return node == node.parent.right;
    }

    /**
     * Удаление узла по ключу
     *
     * @param key
     */
    public void remove(Key key) {
        remove(search(key));
    }

    private void remove(Node node) {
        if (node == null) {
            return;
        }
        Node child, parent;
        boolean color;
        if (node.left != null && node.right != null) {
            Node replace = node.right;
            while (replace.left != null) {
                replace = replace.left;
            }
            if (getParent(node) != null) {
                if (isLeft(node)) {
                    node.parent.left = replace;
                } else {
                    node.parent.right = replace;
                }
            } else {
                this.root = replace;
            }
            child = replace.right;
            parent = getParent(replace);
            color = getColor(replace);
            if (parent == node) {
                parent = replace;
            } else {
                if (child != null) {
                    child.parent = parent;
                }
                parent.left = child;
                replace.right = node.right;
                node.right.parent = replace;
            }
            replace.parent = node.parent;
            replace.color = node.color;
            replace.left = node.left;
            node.left.parent = replace;
            if (color == BLACK) {
                removeFixUp(child, parent);
            }
            return;
        }
        if (node.left != null) {
            child = node.left;
        } else {
            child = node.right;
        }
        parent = node.parent;
        color = node.color;
        if (child != null) {
            child.parent = parent;
        }
        if (parent != null) {
            if (parent.left == node) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        } else {
            this.root = child;
        }
        if (color == BLACK) {
            removeFixUp(child, parent);
        }
    }

    private void removeFixUp(Node node, Node parent) {
        Node other;

        while ((node == null || getColor(node) == BLACK) && (node != root)) {
            if (parent.left == node) {
                other = parent.right;
                if (getColor(other) == RED) {
                    setColor(other, BLACK);
                    setColor(parent, RED);
                    rotateLeft(parent);
                    other = parent.right;
                }

                if ((other.left == null || getColor(other.left) == BLACK) &&
                        (other.right == null || getColor(other.right) == BLACK)) {
                    setColor(other, RED);
                    node = parent;
                    parent = getParent(node);
                } else {
                    if (other.right == null || getColor(other.right) == BLACK) {
                        setColor(other.left, BLACK);
                        setColor(other, RED);
                        rotateRight(other);
                        other = parent.right;
                    }
                    setColor(other, getColor(parent));
                    setColor(parent, BLACK);
                    setColor(other.right, BLACK);
                    rotateLeft(parent);
                    node = root;
                    break;
                }
            } else {
                other = parent.left;
                if (getColor(other) == RED) {
                    setColor(other, BLACK);
                    setColor(parent, RED);
                    rotateRight(parent);
                    other = parent.left;
                }

                if ((other.left == null || getColor(other.left) == BLACK) &&
                        (other.right == null || getColor(other.right) == BLACK)) {
                    setColor(other, RED);
                    node = parent;
                    parent = getParent(node);
                } else {
                    if (other.left == null || getColor(other.left) == BLACK) {
                        setColor(other.right, BLACK);
                        setColor(other, RED);
                        rotateLeft(other);
                        other = parent.left;
                    }
                    setColor(other, getColor(parent));
                    setColor(parent, BLACK);
                    setColor(other.left, BLACK);
                    rotateRight(parent);
                    node = root;
                    break;
                }
            }
        }
        if (node != null) {
            setColor(node, BLACK);
        }
    }

    private boolean getColor(Node node) {
        return node == null ? BLACK : node.color;
    }

    private void setColor(Node node, boolean color) {
        if (node != null) {
            node.color = color;
        }
    }

    private Node getParent(Node node) {
        return node == null ? null : node.parent;
    }

    private Node getGrandParent(Node node) {
        return getParent(getParent(node));
    }

    /**
     * Поиск узла по ключу
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

    void print() {
        int maxHeight = maxHeight();
        printHeight(singletonList(root), 1, maxHeight, 4);
        System.out.println();
    }

    public int maxHeight() {
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
                System.out.print(node.key + ":" + (node.color ? "B" : "R  "));
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
