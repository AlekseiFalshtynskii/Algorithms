package ru.algorithms.lesson12;

import static java.util.concurrent.ThreadLocalRandom.current;
import static org.apache.commons.math3.primes.Primes.nextPrime;

public class ChainingHashTable {

    class HashNode {
        Integer key;
        Integer value;
        HashNode next;

        HashNode(Integer key, Integer value, HashNode next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    class BSTNode extends HashNode {
        BSTNode parent;
        BSTNode left;
        BSTNode right;

        BSTNode(Integer key, Integer value, HashNode next) {
            super(key, value, next);
        }
    }

    class UniversalHash {
        static final int MAX_KEY = 46_000;
        private int m;
        private int a;
        private int b;
        private int p;

        UniversalHash(int m) {
            this.m = m;
            this.p = nextPrime(MAX_KEY);
            this.a = current().nextInt(0, p);
            this.b = current().nextInt(1, p);
        }

        /**
         * Универсальное хэширование
         *
         * @param key
         * @return
         */
        int hash(Integer key) {
            return ((a * key + b) % p) % m;
        }
    }

    private static final int BST_THRESHOLD = 8;

    private HashNode[] table;

    private int initialCapacity = 8;

    private float loadFactor = 0.75f;

    private int size;

    private UniversalHash universalHash;

    ChainingHashTable() {
        table = new HashNode[initialCapacity];
        universalHash = new UniversalHash(table.length);
    }

    /**
     * Получение элемента по ключу
     *
     * @param key
     * @return
     */
    public Integer get(Integer key) {
        HashNode node = table[indexOf(key)];
        if (isBSTNode(node)) {
            BSTNode found = getBST((BSTNode) node, key);
            return found == null ? null : found.value;
        }
        return getChain(node, key);
    }

    /**
     * Получение элемента из цепочки
     *
     * @param node
     * @param key
     * @return
     */
    private Integer getChain(HashNode node, Integer key) {
        while (node != null) {
            if (key.equals(node.key)) {
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    /**
     * Получение элемента из бинарного дерева поиска
     *
     * @param node
     * @param key
     * @return
     */
    private BSTNode getBST(BSTNode node, Integer key) {
        if (key.compareTo(node.key) < 0) {
            return getBST(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            return getBST(node.right, key);
        }
        return node;
    }

    /**
     * Добавление элемента в хэш-таблицу
     *
     * @param key
     * @param value
     */
    public void add(Integer key, Integer value) {
        int index = indexOf(key);
        HashNode node = table[index];
        if (node == null) {
            table[index] = newHashNode(key, value, null);
        } else if (isBSTNode(node)) {
            addBST((BSTNode) node, newBSTNode(key, value, null));
        } else {
            addChain(index, newHashNode(key, value, null));
        }
        size++;

        if ((float) size / table.length >= loadFactor) {
            resize();
        }
    }

    /**
     * Добавление элемента в цепочку
     *
     * @param index
     * @param newNode
     */
    private void addChain(int index, HashNode newNode) {
        HashNode node = table[index];
        int count;
        for (count = 1; ; count++) {
            if (newNode.key.equals(node.key)) {
                node.value = newNode.value;
                return;
            } else if (node.next == null) {
                break;
            } else {
                node = node.next;
            }
        }
        node.next = newNode;
        if (count >= BST_THRESHOLD - 1) {
            transformBST(index);
        }
    }

    /**
     * Добавление элемента в дерево
     *
     * @param node
     * @param newNode
     * @return
     */
    private BSTNode addBST(BSTNode node, BSTNode newNode) {
        if (node == null) {
            return newNode;
        }
        if (newNode.key.compareTo(node.key) < 0) {
            node.left = addBST(node.left, newNode);
            node.left.parent = node;
        } else if (newNode.key.compareTo(node.key) > 0) {
            node.right = addBST(node.right, newNode);
            node.right.parent = node;
        }
        return node;
    }

    /**
     * Преобразование цепочки в дерево
     *
     * @param index
     */
    private void transformBST(int index) {
        HashNode node = table[index];
        BSTNode root = replacementBSTNode(node);
        table[index] = root;
        node = node.next;
        while (node != null) {
            BSTNode newNode = replacementBSTNode(node);
            newNode.parent = root;
            addBST(root, newNode);
            node = node.next;
        }
    }

    private HashNode newHashNode(Integer key, Integer value, HashNode next) {
        return new HashNode(key, value, next);
    }

    private BSTNode newBSTNode(Integer key, Integer value, HashNode next) {
        return new BSTNode(key, value, next);
    }

    private BSTNode replacementBSTNode(HashNode node) {
        return newBSTNode(node.key, node.value, null);
    }

    /**
     * Удаление элемента из хэш-таблицы
     *
     * @param key
     * @return
     */
    public Integer remove(Integer key) {
        int index = indexOf(key);
        HashNode node = table[index];
        if (isBSTNode(node)) {
            BSTNode removed = removeBST((BSTNode) node, key);
            if (removed == null) {
                return null;
            }
            size--;
            return removed.value;
        }
        return removeChain(index, key);
    }

    /**
     * Удаление элемента из цепочки
     *
     * @param index
     * @param key
     * @return
     */
    private Integer removeChain(int index, Integer key) {
        HashNode node = table[index];
        HashNode prev = null;
        while (true) {
            if (node == null) {
                return null;
            } else if (key.equals(node.key)) {
                break;
            } else {
                prev = node;
                node = node.next;
            }
        }
        if (prev == null) {
            table[index] = node.next;
        } else {
            prev.next = node.next;
        }
        size--;
        return node.value;
    }

    /**
     * Удаление элемента из дерева
     *
     * @param node
     * @param key
     * @return
     */
    private BSTNode removeBST(BSTNode node, Integer key) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            node.left = removeBST(node.left, key);
            return node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = removeBST(node.right, key);
            return node;
        }
        if (node.left == null && node.right == null) {
            return null;
        } else if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        }
        BSTNode maxNode = findMaxNode(node.left);
        node.key = maxNode.key;
        node.value = maxNode.value;
        maxNode.parent.right = null;
        maxNode.parent = null;
        return node;
    }

    /**
     * Поиск максимального элемента в поддереве
     *
     * @param node
     * @return
     */
    private BSTNode findMaxNode(BSTNode node) {
        if (node.right == null) {
            return node;
        }
        return findMaxNode(node.right);
    }

    /**
     * Расширение хэш-таблицы в 2 раза при превышении фактора загрузки
     */
    private void resize() {
        HashNode[] oldTable = table;
        initialCapacity *= 2;
        table = new HashNode[initialCapacity];
        universalHash = new UniversalHash(table.length);
        size = 0;
        for (HashNode node : oldTable) {
            if (isBSTNode(node)) {
                traversalAdd((BSTNode) node);
            } else {
                while (node != null) {
                    add(node.key, node.value);
                    node = node.next;
                }
            }
        }
    }

    /**
     * Обход дерева и добавление элементов дерева в расширенную хэш-таблицу
     *
     * @param node
     */
    private void traversalAdd(BSTNode node) {
        traversalAdd(node.left);
        traversalAdd(node.right);
        add(node.key, node.value);
    }

    /**
     * Получение индекса добавляемого элемента по универсальному хэшу
     *
     * @param key
     * @return
     */
    private int indexOf(Integer key) {
        return universalHash.hash(key);
    }

    private boolean isBSTNode(HashNode node) {
        return node instanceof BSTNode;
    }
}
