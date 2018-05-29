package ru.job4j.set.interfaces;

import ru.job4j.myhashmap.hashmap.interfaces.implemented.SimpleMap;

public interface IHashSet<K> extends Iterable<K> {
    boolean add(K key);
    boolean contains(K key);
    boolean remove(K key);
}
