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
            if (index == _size && blockSize == _blockSize) {
                block = addBlock();
                blockSize = 0;
            } else if (index != 0 && index % _blockSize == 0) {
                block = block.getNext();
                blockSize = ((BArray<T>) block.get()).size();
                sumSize += blockSize;
            }

            if (blockSize == _blockSize) {
                OList.ListItem newBlock = addBlock(block);
                copyPartBlock((BArray<T>) block.get(), (BArray<T>)newBlock.get(), index - (sumSize - blockSize));
            }
            addToBlock(block, index - (sumSize - blockSize), element);
        }
    }

    private void addToBlock(OList.ListItem block, int index, T element) {
        BArray<T> bArray = (BArray<T>) block.get();
        bArray.add(index, element);
        _size++;
    }

    private OList.ListItem addBlock() {
        return addBlock(null);
    }

    private OList.ListItem addBlock(OList.ListItem item) {
        BArray<T> newBlock = new BArray<>(_blockSize);
        return _arrs.add(item, newBlock);
    }

    private void copyPartBlock(BArray<T> from, BArray<T> to, int index) {
        int fromIndex = index > _blockSize / 2 ? index : _blockSize / 2;
        int j = 0;
        for (int i = fromIndex; i < _blockSize; i++) {
            to.add(j++, from.get(i));
            from.set(i, null);
            from.incSize();
        }
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
        if (_arrs == null || _arrs.head() == null) {
            return "null";
        }
        OList.ListItem item = _arrs.head();
        StringBuilder result = new StringBuilder(valueOf(item.get())).append(",\n");
        while (item.getNext() != null) {
            item = item.getNext();
            result.append(item.get()).append(",\n");
        }
        return result.deleteCharAt(result.length() - 2).toString();
    }
}
