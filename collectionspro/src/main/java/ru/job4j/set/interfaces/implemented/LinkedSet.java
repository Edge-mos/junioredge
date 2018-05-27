package ru.job4j.set.interfaces.implemented;

import ru.job4j.set.interfaces.Iset;
import ru.job4j.simplelinkedlist.SimpleLinkedList;

import java.util.Iterator;

public class LinkedSet<T> implements Iset<T> {
    private SimpleLinkedList<T> values;

    public LinkedSet() {
        values = new SimpleLinkedList<>();
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
    public Iterator<T> iterator() {
        return this.values.iterator();
    }

//    private class GetIterator implements Iterator<T> {
//        private Iterator<T> it = LinkedSet.this.values.iterator();
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
