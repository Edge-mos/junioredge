package ru.job4j.simplecontainersstackqueue.interfaces;
/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 11.05.2018.
 */
public interface Storable<E> {
    void push(E value);
    E poll();
    int size();
}
