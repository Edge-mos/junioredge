package ru.job4j.set.interfaces.implemented;

import ru.job4j.myhashmap.hashmap.interfaces.Imap;
import ru.job4j.myhashmap.hashmap.interfaces.implemented.SimpleMap;
import ru.job4j.set.interfaces.IHashSet;

import java.util.*;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 28.05.2018.
 */

public class SimpleHashSet<K> implements IHashSet<K> {
    private Imap<K, Object> values;

    public SimpleHashSet() {
        this.values = new SimpleMap<>();
    }

    @Override
    public boolean add(K key) {
     return this.values.insert(key, null);
    }

    @Override
    public boolean contains(K key) {
        return this.values.get(key) != null;
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
                result[index++] = o.toString().replaceAll("->null", "");
            }
            return Arrays.toString(result);
        }
        return Arrays.toString(new Object[0]);
    }

    @Override
    public Iterator<K> iterator() {
        return this.values.keySet().iterator();
    }
}
