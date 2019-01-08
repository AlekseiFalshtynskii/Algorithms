package ru.lesson02;

import static java.lang.String.valueOf;

public class PQueue<T> {
    private static final int BLOCK_SIZE = 5;
    private BArray<OList<T>> _queues;
    private int _size;
    private int _priorities;

    void enqueue(int priority, T item) {
        if (priority <= 0) {
            throw new IllegalArgumentException(valueOf(priority));
        }
        if (_queues == null) {
            _queues = new BArray<>(BLOCK_SIZE);
        }
        for (int i = _priorities; i < priority; i++) {
            _queues.add(null);
            _priorities++;
        }
        OList<T> queue = _queues.get(priority - 1);
        if (queue == null) {
            queue = new OList<>();
        }
        queue.add(item);
        _queues.set(priority - 1, queue);
        _size++;
    }

    T dequeue() {
        if (_size == 0) {
            return null;
        }
        T element = null;
        for (int i = 0; i < _priorities; i++) {
            if (_queues.get(i) != null) {
                element = _queues.get(i).head().get();
                _queues.get(i).remove(_queues.get(i).head());
                if (_queues.get(i).head() == null) {
                    _queues.set(i, null);
                }
                break;
            }
        }
        _size--;
        return element;
    }

    @Override
    public String toString() {
        if (_queues == null) {
            return "";
        }
        return _queues.toString();
    }
}
