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
    public void add(T value) {
        Thread addThread = new Thread(new Runnable() {
            @Override
            public void run() {
                SyncronizedArrayList.this.checkCapacity();
                synchronized (monitor) {
                    SyncronizedArrayList.this.values[index++] = value;
                    SyncronizedArrayList.this.threadSleep();
                }
            }
        });
        this.threadProcess(addThread);
    }

    @Override
    public T get(int index) {
        final Object[] result = new Object[1];
        Thread getThread = new Thread(new Runnable() {
            @Override
            public void run() {
                SyncronizedArrayList.this.checkCapacity();
                synchronized (monitor) {
                    result[0] = SyncronizedArrayList.this.values[index];
                    SyncronizedArrayList.this.threadSleep();
                }
            }
        });
        this.threadProcess(getThread);
        return ((T) result[0]);
    }

    @Override
    public void delete(int position) {
        Thread deleteThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (monitor) {
                    SyncronizedArrayList.this.checkRange(position);
                    System.arraycopy(values, position + 1, values, position, values.length - 1 - position);
                    values[values.length - 1] = null;
                    SyncronizedArrayList.this.index--;
                    SyncronizedArrayList.this.threadSleep();
                }
            }
        });
        this.threadProcess(deleteThread);
    }

    @Override
    public int size() {
        synchronized (monitor) {
            return this.values.length;
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
        if (this.index >= this.size()) {
            synchronized (monitor) {
                this.values = Arrays.copyOf(this.values, this.size() * 2);
            }
        }
    }

    private void threadSleep() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void threadProcess(Thread thread) {
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupt exeption");
            e.printStackTrace();
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
