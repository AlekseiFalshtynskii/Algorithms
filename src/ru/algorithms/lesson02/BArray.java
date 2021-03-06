/*********************************************************
 ������ �������������:

 DArray<Integer> a = new DArray<Integer>();

 Date start = new Date();
 for(int i=0; i<10; i++)
 a.add(i,i*i);

 for(int i=0; i<10; i++)
 System.out.println(a.get(i));
 *********************************************************/

package ru.algorithms.lesson02;

import java.util.Arrays;

import static java.util.Arrays.asList;

public class BArray<T> implements Cloneable {
    private Object[] _arr;
    private int _blockSize;
    private int _size = 0;

    public BArray(int blockSize) {
        _blockSize = blockSize;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T) _arr[index];
    }

    private void relocate(int newSize, int index) {
        Object[] tmp = new Object[newSize];

        if (_arr != null)
            for (int i = 0; i < _arr.length; i++)
                if (i < index)
                    tmp[i] = _arr[i];
                else
                    tmp[i + 1] = _arr[i];
        _arr = tmp;
    }

    public void add(T element) {
        add(_size, element);
    }

    public void add(int index, T element) {
        if (_arr == null || _arr.length <= index)
            relocate(index + _blockSize, index);
        else if (_size % _blockSize == 0)
            relocate(_size + _blockSize, index);
        if (_arr[index] != null) {
            for (int i = size() - 1; i >= index; i--) {
                _arr[i + 1] = _arr[i];
            }
        }
        _arr[index] = element;
        _size++;
    }

    public T remove(int index) {
        T o = (T) _arr[index];
        for (int i = index; i <= size() - 2; i++) {
            _arr[i] = _arr[i + 1];
        }
        _arr[size() - 1] = null;
        _size--;
        return o;
    }

    public void set(int index, T element) {
        _arr[index] = element;
    }

    void incSize() {
        _size--;
    }

    public int size() {
        return _size;
    }

    public <T> T[] toArray(T[] a) {
        return asList(_arr).toArray(a);
    }

    public void clear() {
        _arr = null;
        _size = 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return Arrays.toString(_arr);
    }
}
