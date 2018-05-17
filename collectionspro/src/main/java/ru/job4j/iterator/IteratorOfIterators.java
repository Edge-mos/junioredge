package ru.job4j.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorOfIterators {

    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {

        return new Iterator<Integer>() {
            Iterator<Integer> current = it.next();

            @Override
            public boolean hasNext() {
                if (current.hasNext()) {
                    return true;
                }
                if (it.hasNext()) {
                    current = it.next();
                    return current.hasNext();
                }
                return false;
            }

            @Override
            public Integer next() {
                if (this.hasNext()) {
                    return current.next();
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

}
