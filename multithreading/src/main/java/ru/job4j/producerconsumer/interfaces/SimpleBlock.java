package ru.job4j.producerconsumer.interfaces;

public interface SimpleBlock<T> {
    boolean offer(T obj);
    T poll();
    int size();
}
