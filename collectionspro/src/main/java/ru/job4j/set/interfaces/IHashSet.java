package ru.job4j.set.interfaces;

public interface IHashSet<K> extends Iterable<K> {
    boolean add(K key);
    boolean contains(K key);
    boolean remove(K key);
}
