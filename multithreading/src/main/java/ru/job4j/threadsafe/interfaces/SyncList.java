package ru.job4j.threadsafe.interfaces;

public interface SyncList<T> extends Iterable<T> {
    void add(T value);
    T get(int index);
    void delete(int position);
    int size();
}
