package ru.job4j.threadsafe.interfaces.implemented;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.threadsafe.interfaces.SyncList;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**SyncronizedArrayList
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 10.07.2018.
 */
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

        synchronized (monitor) {
            this.checkCapacity();
            this.values[index++] = value;
        }
    }


    @Override
    @SuppressWarnings("unchecked")
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
        synchronized (monitor) {
            return this.index;
        }

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
        synchronized (monitor) {
            return new ArrayIterator<>();
        }
    }

    private class ArrayIterator<T> implements Iterator<T> {
        private int itIndex;
        private final int expectedModCount;

        ArrayIterator() {
            synchronized (monitor) {
                this.expectedModCount = SyncronizedArrayList.this.size();
            }
        }

        @Override
        public boolean hasNext() {
            synchronized (monitor) {
                return this.itIndex < SyncronizedArrayList.this.size();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            synchronized (monitor) {
                this.checkForModification();
                if (this.hasNext()) {
                    return ((T) SyncronizedArrayList.this.values[itIndex++]);
                }
                throw new NoSuchElementException();
            }
        }

        final void checkForModification() {
            synchronized (monitor) {
                if (SyncronizedArrayList.this.size() != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        }
    }
}
