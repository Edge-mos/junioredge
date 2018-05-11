package ru.job4j.generics;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

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
        if (this.size() < this.values.length) {
            this.values[this.index++] = model;
            ++this.modCount;
        } else {
           this.values =  Arrays.copyOf(this.values, this.size() * 2);
            this.values[this.index++] = model;
            ++this.modCount;
        }
    }

    public void set(int position, T model) {
        if (this.range(position)) {
            this.values[position] = model;
            this.index++;
            ++this.modCount;
        }
    }

    public void delete(int position) {
        if (this.range(position)) {
            System.arraycopy(this.values, position + 1, this.values, position, this.values.length - position - 1);
            this.index--;
            ++this.modCount;
        }
    }

    public T get(int index) {
      return this.range(index) ? ((T) this.values[index]) : null;
    }

    public int size() {
        return this.index;
    }

    private boolean range(int index) {
      if (index < 0 || index > this.index) {
          throw new IndexOutOfBoundsException(String.format("Index: %d, Size: %d", index, size()));
      }
      return true;
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
            return itIndex < values.length;
        }

        @Override
        public T next() {
            this.checkForModification();
            return ((T) values[itIndex++]);
        }

        final void checkForModification() {
            if (SimpleArray.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
