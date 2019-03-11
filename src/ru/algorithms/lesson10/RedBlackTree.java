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
            if (node.parent == null) {
                root = pivot;
            }
            pivot.parent = node.parent;
            if (node.parent != null) {
                if (isLeft(node)) {
                    node.parent.left = pivot;
                } else {
                    node.parent.right = pivot;
                }
            }
            node.right = pivot.left;
            if (pivot.left != null) {
                pivot.left.parent = node;
            }
            node.parent = pivot;
            pivot.left = node;
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
            if (node.parent == null) {
                root = pivot;
            }
            pivot.parent = node.parent;
            if (node.parent != null) {
                if (isLeft(node)) {
                    node.parent.left = pivot;
                } else {
                    node.parent.right = pivot;
                }
            }
            node.left = pivot.right;
            if (pivot.right != null) {
                pivot.right.parent = node;
            }
            node.parent = pivot;
            pivot.right = node;
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
        if (node.right != null && node.left != null) {
            Node max = searchMax(node.left);
            node.key = max.key;
            node = max;
        }
        Node replacedNode = node.left != null ? node.left : node.right;
        if (replacedNode != null) {
            replaceNode(node, replacedNode);
            node.left = node.right = node.parent = null;
            if (node.color == BLACK) {
                removeFixUp(replacedNode);
            }
        } else if (node.parent == null) {
            root = null;
        } else {
            if (node.color == BLACK) {
                removeFixUp(node);
            }
            if (isLeft(node)) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
            node.parent = null;
        }
    }

    private void removeFixUp(Node node) {
        while (node != root && getColor(node) == BLACK) {
            if (isLeft(node)) {
                Node sibling = getRight(getParent(node));
                if (getColor(sibling) == RED) {
                    setColor(sibling, BLACK);
                    setColor(getParent(node), RED);
                    rotateLeft(getParent(node));
                    sibling = getRight(getParent(node));
                }
                if (getColor(getLeft(sibling)) == BLACK && getColor(getRight(sibling))== BLACK) {
                    setColor(getLeft(sibling), RED);
                    node = getParent(node);
                    continue;
                } else if (getColor(getRight(sibling)) == BLACK) {
                    setColor(getLeft(sibling), BLACK);
                    setColor(sibling, RED);
                    rotateRight(sibling);
                    sibling = getRight(getParent(node));
                }
                if (getColor(getRight(sibling)) == RED) {
                    setColor(sibling, getColor(getParent(node)));
                    setColor(getParent(node), BLACK);
                    setColor(getRight(sibling), BLACK);
                    rotateLeft(getParent(node));
                    node = root;
                }
            } else {
                Node sibling = getLeft(getParent(node));
                if (getColor(sibling) == RED) {
                    setColor(sibling, BLACK);
                    setColor(getParent(node), RED);
                    rotateRight(getParent(node));
                    sibling = getLeft(getParent(node));
                }
                if (getColor(getRight(sibling)) == BLACK && getColor(getLeft(sibling)) == BLACK) {
                    setColor(sibling, RED);
                    node = getParent(node);
                    continue;
                } else if (getColor(getLeft(sibling)) == BLACK) {
                    setColor(getRight(sibling), BLACK);
                    setColor(sibling, RED);
                    rotateLeft(sibling);
                    sibling = getLeft(getParent(node));
                }
                if (getColor(getLeft(sibling)) == RED) {
                    setColor(sibling, getColor(getParent(node)));
                    setColor(getParent(node), BLACK);
                    setColor(getLeft(sibling), BLACK);
                    rotateRight(getParent(node));
                    node = root;
                }
            }
        }
        setColor(node, BLACK);
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

    private Node getLeft(Node node) {
        return node == null ? null : node.left;
    }

    private Node getRight(Node node) {
        return node == null ? null : node.right;
    }

    /**
     * Смена узлов местами
     *
     * @param target
     * @param with
     */
    private void replaceNode(Node target, Node with) {
        if (target.parent == null) {
            root = with;
        } else if (isLeft(target)) {
            target.parent.left = with;
        } else {
            target.parent.right = with;
        }
        with.parent = target.parent;
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

    /**
     * Поиск максимального узла в поддереве
     *
     * @param node
     * @return
     */
    private Node searchMax(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
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
