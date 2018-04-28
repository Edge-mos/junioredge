package ru.job4j.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorArray implements Iterable {

    private final int[][] values;

    public IteratorArray(int[][] values) {
        this.values = values;
    }

    @Override
    public Iterator iterator() {
        return new GetIterator();
    }

    private class GetIterator implements Iterator {
        private int rows = 0;
        private int cols = 0;

        @Override
        public boolean hasNext() {
            return rows < values.length;
        }

        @Override
        public Object next() {
            if (values.length == 0) {
                throw new NoSuchElementException();
            }

            Object result = values[rows][cols++];
            if (cols >= values[rows].length) {
                cols = 0;
                rows++;
            }
            return result;
        }
    }
}
