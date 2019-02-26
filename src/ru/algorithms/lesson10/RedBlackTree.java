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
    }

    private Node root;

    /**
     * Вставка нового узла в дерево
     *
     * @param key
     */
    public void insert(Key key) {
        Node node = new Node(key);
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
            node.parent = temp;
        }
        insertCase1(node);
    }

    /**
     * Текущий узел - корень, только перекрашивание в черный
     *
     * @param node
     */
    private void insertCase1(Node node) {
        if (node.parent == null) {
            node.color = BLACK;
        } else {
            insertCase2(node);
        }
    }

    /**
     * Предок - черный, свойства не нарушаются
     *
     * @param node
     */
    private void insertCase2(Node node) {
        if (node.parent.color == BLACK) {
            return;
        }
        insertCase3(node);
    }

    /**
     * Родителья и дядя - красные, перекрашиваем в черный, деда в красный
     *
     * @param node
     */
    private void insertCase3(Node node) {
        Node uncle = getUncle(node);
        if (uncle != null && uncle.color == RED) {
            node.parent.color = uncle.color = BLACK;
            Node grandParent = getGrandParent(node);
            grandParent.color = RED;
            insertCase1(grandParent);
        } else {
            insertCase4(node);
        }
    }

    /**
     * Родитель и дядя разных цветов - соответственно поворот налево, если узел правый потомок и направо, если левый
     *
     * @param node
     */
    private void insertCase4(Node node) {
        Node grandParent = getGrandParent(node);
        if (isRight(node) && node.parent == grandParent.left) {
            rotateLeft(node.parent);
            node = node.left;
        } else if (isLeft(node) && node.parent == grandParent.right) {
            rotateRight(node.parent);
            node = node.right;
        }
        insertCase5(node);
    }

    /**
     * Родитель красный, дядя черный, текущий узел - левый потомок и родитель - левый потомок, тогда поворот направо относительно деда
     *
     * @param node
     */
    private void insertCase5(Node node) {
        Node grandParent = getGrandParent(node);
        node.parent.color = BLACK;
        grandParent.color = RED;
        if (node == node.parent.left && node.parent == grandParent.left) {
            rotateRight(grandParent);
        } else {
            rotateLeft(grandParent);
        }
    }

    /**
     * Получение деда
     *
     * @param node
     * @return
     */
    private Node getGrandParent(Node node) {
        if (node != null && node.parent != null) {
            return node.parent.parent;
        }
        return null;
    }

    /**
     * Получение дяди
     *
     * @param node
     * @return
     */
    private Node getUncle(Node node) {
        Node grandParent = getGrandParent(node);
        if (grandParent == null) {
            return null;
        }
        if (node.parent == grandParent.left) {
            return grandParent.right;
        }
        return grandParent.left;
    }

    /**
     * Поворот налево
     *
     * @param node
     */
    private void rotateLeft(Node node) {
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

    /**
     * Поворот направо
     *
     * @param node
     */
    private void rotateRight(Node node) {
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
        if (node.left == null && node.right == null) {
            if (node.parent == null) {
                root = null;
            } else if (isLeft(node)) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
        } else if (node.left == null || node.right == null) {
            Node child = node.right == null ? node.left : node.right;
            removeOneChild(node, child);
        } else {
            Node child = searchMax(node.left);
            removeOneChild(node, child);
        }
    }

    /**
     * Удаление узла с не более одним потомком
     *
     * @param node
     * @param child
     */
    private void removeOneChild(Node node, Node child) {
        replaceNode(node, child);
        if (node.color == BLACK) {
            if (child.color == RED) {
                child.color = BLACK;
            } else {
                deleteCase1(child);
            }
        }
    }

    /**
     * Если текущий узел - новый корень - ничего не делать
     *
     * @param node
     */
    private void deleteCase1(Node node) {
        if (node.parent != null) {
            deleteCase2(node);
        }
    }

    /**
     * Брат — красный, меняем цвета родителя и брата и делаем поворот относительно узла
     *
     * @param node
     */
    private void deleteCase2(Node node) {
        Node sibling = getSibling(node);
        if (sibling.color == RED) {
            node.parent.color = RED;
            sibling.color = BLACK;
            if (isLeft(node)) {
                rotateLeft(node.parent);
            } else {
                rotateRight(node.parent);
            }
        }
        deleteCase3(node);
    }

    /**
     * Родитель, брат и потомки брата - черные - перекрашиваем брата в красный
     *
     * @param node
     */
    private void deleteCase3(Node node) {
        Node sibling = getSibling(node);
        if (node.parent.color == BLACK
                && sibling.color == BLACK
                && sibling.left.color == BLACK
                && sibling.right.color == BLACK) {
            sibling.color = RED;
            deleteCase1(node.parent);
        } else {
            deleteCase4(node);
        }
    }

    /**
     * Брат и его дети ёрные, но родитель красный - просто меняем цвета родителя и брата
     *
     * @param node
     */
    private void deleteCase4(Node node) {
        Node sibling = getSibling(node);
        if (node.parent.color == RED
                && sibling.color == BLACK
                && sibling.left.color == BLACK
                && sibling.right.color == BLACK) {
            sibling.color = RED;
            node.parent.color = BLACK;
        } else {
            deleteCase5(node);
        }
    }

    /**
     * Брат — чёрный, левый потомок брата — красный, правый потомок брата — чёрный
     * Вращаем дерево направо вокруг брата если узел - левый потомок и налево - если правый
     *
     * @param node
     */
    private void deleteCase5(Node node) {
        Node sibling = getSibling(node);
        if (sibling.color == BLACK) {
            if (isLeft(node)
                    && sibling.right.color == BLACK
                    && sibling.left.color == RED) {
                sibling.color = RED;
                sibling.left.color = BLACK;
                rotateRight(sibling);
            } else if (isRight(node)
                    && sibling.left.color == BLACK
                    && sibling.right.color == RED) {
                sibling.color = RED;
                sibling.right.color = BLACK;
                rotateLeft(sibling);
            }
        }
        deleteCase6(node);
    }

    /**
     * Брат — чёрный, правый потомок брата — красный, вращаем дерево налево вокруг родителя, если узел левый потомк и направо, если правый
     *
     * @param node
     */
    private void deleteCase6(Node node) {
        Node sibling = getSibling(node);
        sibling.color = node.parent.color;
        node.parent.color = BLACK;
        if (isLeft(node)) {
            sibling.right.color = BLACK;
            rotateLeft(node.parent);
        } else {
            sibling.left.color = BLACK;
            rotateRight(node.parent);
        }
    }

    /**
     * Смена узлов местами
     *
     * @param node
     * @param child
     */
    private void replaceNode(Node node, Node child) {
        if (node.parent == null) {
            root = child;
        } else if (isLeft(node)) {
            node.parent.left = child;
        } else {
            node.parent.right = child;
        }
        child.parent = node.parent;
        child.right = node.right;
    }

    /**
     * Получение брата
     *
     * @param node
     * @return
     */
    private Node getSibling(Node node) {
        if (isLeft(node)) {
            return node.parent.right;
        }
        return node.parent.left;
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
        if (node.right == null) {
            return node;
        }
        return searchMax(node.right);
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
