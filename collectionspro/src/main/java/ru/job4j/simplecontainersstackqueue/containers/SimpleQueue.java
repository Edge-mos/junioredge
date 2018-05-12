package ru.job4j.simplecontainersstackqueue.containers;

import ru.job4j.simplecontainersstackqueue.interfaces.implemented.AbstractContainer;
/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 11.05.2018.
 */
public class SimpleQueue<E> extends AbstractContainer<E> {

    @Override
    public void push(E value) {
        this.list.addFirst(value);
    }
}
