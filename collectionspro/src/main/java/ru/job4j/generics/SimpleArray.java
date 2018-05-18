package ru.job4j.generics;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 01.05.2018.
 */
public class SimpleArray<T> implements Iterable<T> {
    private Object[] values;
    private int index;
    private int modCount = 0;

    public SimpleArray(int capacity) {
        this.values = new Object[capacity];
    }

    public void add(T model) {
        this.checkCapacity();
        this.values[this.index++] = model;
        ++this.modCount;
    }

    public void set(int position, T model) {
        this.range(position);
        this.values[position] = model;
        ++this.modCount;

    }

    public void delete(int position) {
        this.range(position);
        System.arraycopy(this.values, position + 1, this.values, position, this.values.length - 1 - position);
        this.values[this.values.length - 1] = null;
        this.index--;
        ++this.modCount;
    }

    public T get(int index) {
        this.range(index);
        return ((T) this.values[index]);
    }

    public int size() {
        return this.index;
    }

    private void checkCapacity() {
        if (this.size() >= this.values.length) {
            this.values =  Arrays.copyOf(this.values, this.size() * 2);
        }
    }

    private void range(int index) {
        if (index < 0 || index > this.index) {
            throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", index, size()));
        }
    }

    @Override
    public String toString() {
        Object[] result = new Object[this.index];
        System.arraycopy(this.values, 0, result, 0, this.index);
        return "SimpleArray [ "
                +
                Arrays.toString(result)
                +
                " ]";
    }

    @Override
    public Iterator<T> iterator() {
        return new GetIterator();
    }

    private class GetIterator implements Iterator<T> {
        private int itIndex;
        private int expectedModCount;

        public GetIterator() {
            this.expectedModCount = SimpleArray.this.modCount;
        }

        @Override
        public boolean hasNext() {
            return itIndex < SimpleArray.this.size();
        }

        @Override
        public T next() {
            this.checkForModification();
            if (this.hasNext()) {
                return ((T) values[itIndex++]);
            }
            throw new NoSuchElementException();

        }

        final void checkForModification() {
            if (SimpleArray.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
