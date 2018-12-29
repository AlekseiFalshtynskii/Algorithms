/*********************************************************
 ������ �������������:
 OList<Integer> l = new OList<Integer>();
 for(int i=0; i<10; i++)
 l.add(i*i);

 OList<Integer>.ListItem<Integer> li = l.head();
 while (li != null) {
 System.out.println(li.get());
 li = li.getNext();
 }
 *********************************************************/

package ru.lesson02;

class OList<T> {

    @SuppressWarnings("hiding")
    class ListItem<T> {
        private T _item;
        private ListItem<T> _next;

        ListItem(T item) {
            _item = item;
            _next = null;
        }

        void set(T item) {
            _item = item;
        }

        T get() {
            return _item;
        }

        void setNext(ListItem<T> item) {
            _next = item;
        }

        ListItem<T> getNext() {
            return _next;
        }
    }

    private ListItem<T> _head;
    private ListItem<T> _tail;

    OList() {
        _head = null;
        _tail = null;
    }

    ListItem<T> head() {
        return _head;
    }

    void add(T item) {
        ListItem<T> li = new ListItem<>(item);
        if (_head == null) {
            _head = li;
            _tail = li;
        } else {
            _tail.setNext(li);
            _tail = li;
        }
    }

    void add(ListItem<T> listItem, T item) {
        if (listItem == _tail) {
            add(item);
        } else {
            ListItem<T> next = listItem.getNext();
            ListItem<T> li = new ListItem<>(item);
            listItem.setNext(li);
            li.setNext(next);
        }
    }

    void remove(ListItem<T> listItem) {
        if (listItem == _head) {
            _head = listItem.getNext();
        } else {
            ListItem<T> block = _head;
            while (block.getNext() != listItem) {
                block = block.getNext();
            }
            block.setNext(block.getNext().getNext());
            if (listItem == _tail) {
                _tail = block;
            }
        }
    }
}
