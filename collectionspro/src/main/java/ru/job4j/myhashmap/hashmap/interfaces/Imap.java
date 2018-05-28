package ru.job4j.myhashmap.hashmap.interfaces;

public interface Imap<K, V> extends Iterable {
    boolean insert(K key, V value);
    V get(K key);
    boolean delete(K key);
    int size();
    boolean isEmpty();
    void clear();
}
