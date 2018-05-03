package ru.job4j.generics;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 01.05.2018.
 */
public class SimpleArray<T> implements Iterable<T> {

   private Object[] values;
   private int index;

    public SimpleArray(int capasity) {
        this.values = new Object[capasity];
    }


    public void add(T model) {
        if (this.size() < this.values.length) {
            this.values[this.index++] = model;
        } else {
           this.values =  Arrays.copyOf(this.values, this.size() * 2);
            this.values[this.index++] = model;
        }
    }

    public void set(int position, T model) {
        if (range(position)) {
            this.values[position] = model;
            this.index++;
        }
    }

    public void delete(int position) {
        if (range(position)) {
            System.arraycopy(this.values, position + 1, this.values, position, this.values.length - position - 1);
            this.index--;
        }
    }

    public T get(int index) {
      return range(index) ? ((T) this.values[index]) : null;
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

        @Override
        public boolean hasNext() {
            return itIndex < values.length;
        }

        @Override
        public T next() {
            return ((T) values[itIndex++]);
        }
    }
}
