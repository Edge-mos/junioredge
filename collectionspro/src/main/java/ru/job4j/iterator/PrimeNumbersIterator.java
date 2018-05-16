package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.lang.Math.sqrt;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 30.04.2018.
 */

public class PrimeNumbersIterator implements Iterable {

    private int[] values;

    public PrimeNumbersIterator(int[] values) {
        this.values = values;
    }

    @Override
    public Iterator iterator() {
        return new GetIterator();
    }

    private class GetIterator implements Iterator {
        private int index;
        private int[] array = PrimeNumbersIterator.this.values;

        @Override
        public boolean hasNext() {
            return this.search();
        }

        @Override
        public Object next() {
            if (this.hasNext()) {
                return PrimeNumbersIterator.this.values[index++];
            } else {
                throw new NoSuchElementException();
            }

        }

        private boolean search() {
            for (int i = this.index; i < array.length; i++) {
                if (array[i] < 2) {
                    continue;
                }
                if (array[i] == 2) {
                    this.index = i;
                    return true;
                }
                if (array[i] % 2 == 0) {
                    continue;
                }
                int value = array[i];
                for (int j = 2; j <= sqrt(value); j++) {
                    if (j % 2 == 0) {
                        continue;
                    }
                    if (value % j == 0) {
                        break;
                    }
                }
                this.index = i;
                return true;
            }
            return false;
        }
    }
}
