package ru.job4j.threadsafe.interfaces.implemented;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.threadsafe.interfaces.SyncList;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**SyncronizedLinkedList
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 10.07.2018.
 */
@ThreadSafe
public class SyncronizedLinkedList<T> implements SyncList<T> {
    @GuardedBy("lock")
    private Node<T> head;
    private int size;
    private final Object lock = new Object();

    @Override
    public void add(T value) {
        synchronized (lock) {
            if (this.head == null) {
                this.head = new SyncronizedLinkedList.Node<>(value);
            } else {
                SyncronizedLinkedList.Node<T> tmp = this.head;
                while (tmp.next != null) {
                    tmp = tmp.next;
                }
                tmp.next = new SyncronizedLinkedList.Node<>(value);
            }
            this.size++;
        }
    }

    @Override
    public T get(int index) {
        synchronized (lock) {
            this.checkRange(index);
            SyncronizedLinkedList.Node<T> tmp = this.head;
            for (int i = 0; i < index; i++) {
                tmp = tmp.next;
            }
            return tmp.data;
        }
    }

    @Override
    public void delete(int position) {
        synchronized (lock) {
            this.checkRange(position);
            if (position == 0) {
                this.head = this.head.next;
            } else {
                SyncronizedLinkedList.Node<T> tmp = this.head;
                for (int i = 0; i < position - 1; i++) {
                    tmp = tmp.next;
                }
                tmp.next = tmp.next.next;
            }
            this.size--;
        }
    }

    private void checkRange(int index) {
        if (index < 0 || index >= this.size) {
            throw new NoSuchElementException(String.format("Index: %d, Size: %d", index, this.size));
        }
    }

    @Override
    public int size() {
        synchronized (lock) {
            return this.size;
        }
    }

    private static class Node<T> {
        private T data;
        private Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    @Override
    public String toString() {
        synchronized (lock) {
            Object[] result = new Object[this.size];
            int index = 0;
            SyncronizedLinkedList.Node tmp = this.head;
            while (tmp != null) {
                result[index++] = tmp.data;
                tmp = tmp.next;
            }
            return Arrays.toString(result);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<>();
    }

    private class ListIterator<T> implements Iterator<T> {
        private int itIndex;
        private final int expectedModCount;

        ListIterator() {
            this.expectedModCount = SyncronizedLinkedList.this.size;
        }

        @Override
        public boolean hasNext() {
            synchronized (lock) {
                return this.itIndex < SyncronizedLinkedList.this.size;
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            synchronized (lock) {
                if (this.hasNext()) {
                    this.checkForModification();
                    return ((T) SyncronizedLinkedList.this.get(this.itIndex++));
                }
                throw new NoSuchElementException();
            }
        }

        final void checkForModification() {
            synchronized (lock) {
                if (this.expectedModCount != SyncronizedLinkedList.this.size) {
                    throw new ConcurrentModificationException();
                }
            }
        }
    }
}
