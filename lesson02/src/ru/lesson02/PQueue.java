package ru.lesson02;

import static java.lang.String.valueOf;

public class PQueue<T> {
    private static final int BLOCK_SIZE = 5;
    private OList<BArray<T>> _queues;
    private int _size;

    void enqueue(int priority, T item) {
        if (priority <= 0) {
            throw new IllegalArgumentException(valueOf(priority));
        }
        if (_queues == null) {
            _queues = new OList<>();
            _queues.add(null);
        }
        OList.ListItem queue = _queues.head();
        int size = queue.get() == null ? 0 : ((BArray<T>) queue.get()).size();
        for (int i = 1; i < priority; i++) {
            if (queue.getNext() == null) {
                _queues.add(null);
            }
            queue = queue.getNext();
            size = queue.get() == null ? 0 : ((BArray<T>) queue.get()).size();
        }
        if (queue.get() == null) {
            queue.set(new BArray<T>(BLOCK_SIZE));
        }
        ((BArray<T>) queue.get()).add(size, item);
        _size++;
    }

    T dequeue() {
        if (_size == 0) {
            return null;
        }
        OList.ListItem queue = _queues.head();
        while (queue.getNext() != null && queue.get() == null) {
            queue = queue.getNext();
        }
        T item = ((BArray<T>) queue.get()).remove(0);
        if (((BArray<T>) queue.get()).size() == 0) {
            queue.set(null);
        }
        _size--;
        return item;
    }

    @Override
    public String toString() {
        if (_queues == null) {
            return "";
        }
        OList.ListItem queue = _queues.head();
        int priority = 0;
        StringBuilder result = new StringBuilder(valueOf(++priority)).append(": ").append(queue.get()).append("\n");
        while(queue.getNext() != null) {
            queue = queue.getNext();
            result.append(++priority).append(": ").append(queue.get()).append("\n");
        }
        return result.toString();
    }
}
