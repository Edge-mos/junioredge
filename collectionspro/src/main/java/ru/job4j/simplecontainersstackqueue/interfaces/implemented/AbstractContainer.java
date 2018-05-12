package ru.job4j.simplecontainersstackqueue.interfaces.implemented;

import ru.job4j.simplecontainersstackqueue.interfaces.Storable;
import ru.job4j.simplelinkedlist.SimpleLinkedList;

import java.util.NoSuchElementException;
/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 11.05.2018.
 */
public abstract class AbstractContainer<E> implements Storable<E> {
    protected SimpleLinkedList<E> list = new SimpleLinkedList<>();

    @Override
    public abstract void push(E value);

    @Override
    public E poll() {
        if (this.size() == 0) {
            throw new NoSuchElementException(String.format("No elements, size: %d", this.size()));
        }
        return this.list.deleteLast();
    }

    @Override
    public int size() {
        return this.list.getSize();
    }
}
