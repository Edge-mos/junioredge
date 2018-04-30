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
        private int getIndex;

        @Override
        public boolean hasNext() {
            for (int i = index; i < values.length; i++) {
                if (search(values[i])) {
                    index = i;
                    index++;
                    return true;
                }
            }
            return false;
        }

        @Override
        public Object next() {
            for (int i = getIndex; i < values.length; i++) {
                if (search(values[i])) {
                    return values[getIndex++];
                } else {
                    getIndex++;
                }
            }
            throw new NoSuchElementException();
        }

        private boolean search(int number) {
            boolean result = true;
            if (number == 2) {
                return true;
            }
            if (number < 2) {
                return false;
            }
            for (int i = 1; i <= sqrt(number); i++) {
                if (number % 2 == 0) {
                    result =  false;
                }
            }
            return result;
        }
    }
}
