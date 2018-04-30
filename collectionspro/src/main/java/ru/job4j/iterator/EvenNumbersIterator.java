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
        private int getIndex;

        @Override
        public boolean hasNext() {
            index = search(index);
            if (index != -1) {
                index++;
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            getIndex = search(getIndex);
            if (getIndex != -1) {
               return values[getIndex++];
            }
            throw new NoSuchElementException();
        }

        private int search(int searchIndex) {
            for (int i = searchIndex; i < values.length; i++) {
                if (values[i] % 2 == 0) {
                    return i;
                }
            }
            return -1;
        }
    }
}
