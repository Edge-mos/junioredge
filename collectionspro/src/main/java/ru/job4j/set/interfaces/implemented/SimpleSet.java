package ru.job4j.set.interfaces.implemented;

import ru.job4j.generics.SimpleArray;
import ru.job4j.set.interfaces.Iset;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class SimpleSet<T> implements Iset<T> {
    private SimpleArray<T> values;

    public SimpleSet() {
        this.values = new SimpleArray<>(10);
    }

    @Override
    public void add(T model) {
        for (T value : this.values) {
            if (value.equals(model)) {
                return;
            }
        }
        this.values.add(model);
    }

    @Override
    public String toString() {
        return this.values.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return this.values.iterator();
    }

//    private class GetIterator implements Iterator<T> {
//        private Iterator<T> it = SimpleSet.this.values.iterator();
//
//        @Override
//        public boolean hasNext() {
//            return this.it.hasNext();
//        }
//
//        @Override
//        public T next() {
//            return this.it.next();
//        }
//    }
}
