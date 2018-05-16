package ru.job4j.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 29.04.2018.
 */
public class EvenNumbersIterator implements Iterable {
    private final int[] values;

    public EvenNumbersIterator(int[] values) {
        this.values = values;
    }

    @Override
    public Iterator iterator() {
        return new GetIterator();
    }

    private class GetIterator implements Iterator {
        private int index;

        @Override
        public boolean hasNext() {
            return this.search();
        }

        @Override
        public Object next() {
            if (this.hasNext()) {
                return EvenNumbersIterator.this.values[index++];
            } else {
                throw new NoSuchElementException();
            }
        }

        private boolean search() {
            for (int i = this.index; i < EvenNumbersIterator.this.values.length; i++) {
                if (EvenNumbersIterator.this.values[i] % 2 == 0) {
                    this.index = i;
                    return true;
                }
            }
            return false;
        }
    }
}
