package ru.job4j.threadsafe.interfaces.implemented;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.threadsafe.interfaces.SyncList;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

@ThreadSafe
public class SyncronizedArrayList<T> implements SyncList<T> {

    @GuardedBy("monitor")
    private Object[] values;
    private int index;
    private final Object monitor = new Object();

    public SyncronizedArrayList(int capacity) {
        this.values = new Object[capacity];
    }

    @Override
    public void  add(T value) {
        // TODO: 7/9/18 почему racecondition?

        synchronized (monitor) {
            this.checkCapacity();
            this.values[index++] = value;
        }
    }

    @Override
    public T get(int index) {
        synchronized (monitor) {
            this.checkRange(index);
            return ((T) this.values[index]);
        }
    }

    @Override
    public void delete(int position) {
        synchronized (monitor) {
            this.checkRange(position);
            System.arraycopy(values, position + 1, values, position, values.length - 1 - position);
            values[values.length - 1] = null;
            this.index--;
        }
    }

    @Override
    public int size() {
        return this.index;
    }

    private void checkRange(int index) {
        synchronized (monitor) {
            if (index < 0 || index >= this.size()) {
                throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", index, this.size()));
            }
        }
    }

    private void checkCapacity() {
        synchronized (monitor) {
            if (this.size() >= this.values.length) {
                this.values = Arrays.copyOf(this.values, this.size() * 2);
            }
        }
    }

    @Override
    public String toString() {
        Object[] result = new Object[this.index];
        int index = 0;
        synchronized (monitor) {
            System.arraycopy(this.values, 0, result, 0, this.index);
        }
        return Arrays.toString(result);
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<>();
    }

    private class ArrayIterator<T> implements Iterator<T> {
        private int itIndex;

        @Override
        public boolean hasNext() {
            return this.itIndex < SyncronizedArrayList.this.size();
        }

        @Override
        public T next() {
            if (this.hasNext()) {
                synchronized (monitor) {
                    return ((T) SyncronizedArrayList.this.values[itIndex++]);
                }
            }
            throw new NoSuchElementException();
        }
    }
}
