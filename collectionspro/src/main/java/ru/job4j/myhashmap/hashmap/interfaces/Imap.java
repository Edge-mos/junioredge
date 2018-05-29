package ru.job4j.myhashmap.hashmap.interfaces;

import ru.job4j.set.interfaces.implemented.SimpleSet;
import ru.job4j.simplelinkedlist.SimpleLinkedList;

public interface Imap<K, V> extends Iterable {
    boolean insert(K key, V value);
    V get(K key);
    boolean delete(K key);
    int size();
    boolean isEmpty();
    void clear();
    SimpleSet<K> keySet();
    SimpleLinkedList<V> values();
}
