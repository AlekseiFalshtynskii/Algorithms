package ru.algorithms.lesson11;

/**
 * Структура для совместной сортировки ключей и частот
 *
 * @param <T>
 */
public class DataNode<T extends Comparable<T>> implements Comparable<DataNode> {

    T key;

    Integer freq;

    public DataNode(T key, Integer freq) {
        this.key = key;
        this.freq = freq;
    }

    @Override
    public int compareTo(DataNode o) {
        return this.key.compareTo((T) o.key);
    }

    @Override
    public String toString() {
        return key + ":" + freq;
    }
}
