package ru.job4j.set.interfaces.implemented;

import ru.job4j.myhashmap.hashmap.interfaces.Imap;
import ru.job4j.myhashmap.hashmap.interfaces.implemented.SimpleMap;
import ru.job4j.set.interfaces.IHashSet;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashSet<K> implements IHashSet<K> {
    private Imap<K, Object> values;
    //private final static Object dummy = new Object();

    public SimpleHashSet() {
        this.values = new SimpleMap<>();
    }

    @Override
    public boolean add(K key) {
        if (this.contains(key)) {
            return false;
        }
        return this.values.insert(key, null);
    }

    @Override
    public boolean contains(K key) {
        if (this.values.get(key) != null){
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(K key) {
        return this.values.delete(key);
    }

    @Override
    public String toString() {
        Object[] result;
        int index = 0;
        if (!this.values.isEmpty()) {
            result = new Object[this.values.size()];
            for (Object o : values) {
                result[index++] = o.toString().replaceAll("->null","");
            }
            return Arrays.toString(result);
        }
        return Arrays.toString(result = new Object[0]);
    }

    @Override
    public Iterator<K> iterator() {
        return new GetIterator();
    }

    private class GetIterator implements Iterator<K> {
        private Iterator<K> it = SimpleHashSet.this.values.iterator();

        @Override
        public boolean hasNext() {
            return this.it.hasNext();
        }

        @Override
        public K next() {
            if (this.hasNext()) {
                return this.it.next();
            }
            throw new NoSuchElementException();
        }
    }
}
