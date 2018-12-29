package ru.lesson02;

import static java.lang.String.valueOf;

public class IArray<T> {
    private OList<BArray<T>> _arrs;
    private int _blockSize;
    private int _size = 0;

    IArray(int blockSize) {
        _blockSize = blockSize;
    }

    void add(T element) {
        add(_size, element);
    }

    void add(int index, T element) {
        if (index < 0 || index > _size) {
            throw new IndexOutOfBoundsException(valueOf(index));
        }
        if (_arrs == null) {
            _arrs = new OList<>();
            _arrs.add(new BArray<>(_blockSize));
            addToBlock(_arrs.head(), index, element);
        } else {
            OList.ListItem block = _arrs.head();
            int blockSize = ((BArray<T>) block.get()).size();
            int sumSize = blockSize;
            while (index > sumSize) {
                block = block.getNext();
                blockSize = ((BArray<T>) block.get()).size();
                sumSize += blockSize;
            }
            addToBlock(block, index - (sumSize - blockSize), element);
        }
    }

    private void addToBlock(OList.ListItem block, int index, T element) {
        BArray<T> bArray = (BArray<T>) block.get();
        bArray.add(index, element);
        if (bArray.size() == _blockSize) {
            addBlock(block);
        }
        _size++;
    }

    private void addBlock(OList.ListItem item) {
        BArray<T> prevBlock = (BArray<T>) item.get();
        BArray<T> newBlock = new BArray<>(_blockSize);
        int j = 0;
        for (int i = (_blockSize / 2); i < _blockSize; i++) {
            newBlock.add(j++, prevBlock.get(i));
            prevBlock.set(i, null);
            prevBlock.incSize();
        }
        _arrs.add(item, newBlock);
    }

    T get(int index) {
        if (index < 0 || index >= _size) {
            throw new IndexOutOfBoundsException(valueOf(index));
        }
        OList.ListItem block = _arrs.head();
        int blockSize = ((BArray<T>) block.get()).size();
        int sumSize = blockSize;
        while (index >= sumSize) {
            block = block.getNext();
            blockSize = ((BArray<T>) block.get()).size();
            sumSize += blockSize;
        }
        return ((BArray<T>) block.get()).get(index - (sumSize - blockSize));
    }

    void remove(int index) {
        if (index < 0 || index >= _size) {
            throw new IndexOutOfBoundsException(valueOf(index));
        }
        OList.ListItem block = _arrs.head();
        int blockSize = ((BArray<T>) block.get()).size();
        int sumSize = blockSize;
        while (index >= sumSize) {
            block = block.getNext();
            blockSize = ((BArray<T>) block.get()).size();
            sumSize += blockSize;
        }
        if (blockSize == 1) {
            _arrs.remove(block);
        } else {
            ((BArray<T>) block.get()).remove(index - (sumSize - blockSize));
        }
        _size--;
    }

    @Override
    public String toString() {
        if (_arrs == null) {
            return "null";
        }
        OList.ListItem item = _arrs.head();
        StringBuilder result = new StringBuilder(valueOf(item.get())).append(",\n");
        while(item.getNext() != null) {
            item = item.getNext();
            result.append(item.get()).append(",\n");
        }
        return result.deleteCharAt(result.length() - 2).toString();
    }
}
