package ru.job4j.simplelinkedlist;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 10.05.2018.
 */

public class SimpleLinkedList<E> implements Iterable<E> {
    private int size;
    private Node<E> first;
    private int modCount = 0;

    /**
     * Метод вставляет в начало списка!
     * @param data параметризированный аргумент.
     */
    public void addFirst(E data) {
        SimpleLinkedList.Node<E> temp = new Node<>(data);
        temp.next = this.first;
        this.first = temp;
        this.size++;
    }

    /**
     * Добавляет элемент в конец списка.
     * @param data параметризированный аргумент.
     */
    public void add(E data) {
        if (this.first == null) {
            addFirst(data);
        } else {
            SimpleLinkedList.Node<E> temp = this.first;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = new Node<>(data);
            this.size++;
        }
    }

    /**
     * Удаляет первый элемент из списка.
     * @return возвращает значение первого элемента.
     */
    public E deleteFirst() {
        SimpleLinkedList.Node<E> tmp = this.first;
        this.first = tmp.next;
        this.size--;
        return tmp.data;
    }

    /**
     * Удаляет элемент по индексу.
     * @param index индекс удаляемого элемента.
     * @return возвращает значение удаляемого элемента.
     */
    public E delete(int index) {
        if (index == 0) {
            return deleteFirst();
        }
        if (range(index)) {
            SimpleLinkedList.Node<E> temp = this.first;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.next;
            }
            SimpleLinkedList.Node<E> deleted = temp.next;
            temp.next = temp.next.next;
            this.size--;
            return deleted.data;
        }
        return null;
    }

    /**
     * Удаляет последний элемент из списка.
     * @return возвращает значение удаляемго элемента.
     */
    public E deleteLast() {
        return delete(this.size - 1);
    }

    /**
     * Метод получения элемента по индексу.
     * @param index искомый индекс.
     * @return параметризированное значение элемента.
     */
    public E get(int index) {
        if (range(index)) {
            SimpleLinkedList.Node<E> temp = this.first;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
            return temp.data;
        }
        return null;
    }

    /**
     *
     * @return возвращает размер коллекции.
     */
    public int getSize() {
        return this.size;
    }

    private boolean range(int index) {
        if (index < 0 || index >= this.size) {
            throw new NoSuchElementException(String.format("Index: %d, Size: %d", index, this.size));
        }
        return true;
    }

    @Override
    public String toString() {
        Object[] result = new Object[this.size];
        SimpleLinkedList.Node<E> temp = this.first;
        for (int i = 0; i < this.size; i++) {
            result[i] = temp.data;
            temp = temp.next;
        }
        return Arrays.toString(result);
    }

    @Override
    public Iterator<E> iterator() {
        return new GetIterator();
    }

    private static class Node<E> {
        private E data;
        private Node<E> next;

        public Node(E data) {
            this.data = data;
        }
    }

    private class GetIterator implements Iterator<E> {
        private int index;
        private int expectedModCount;
        // TODO: 5/11/18 Разобраться!
        private SimpleLinkedList.Node<E> lastReturned = SimpleLinkedList.this.first;
        private SimpleLinkedList.Node<E> nextValue;

        @Override
        public boolean hasNext() {
            return this.index < SimpleLinkedList.this.size;
        }

        @Override
        public E next() {
            if (this.index == 0) {
                this.index++;
                return this.lastReturned.data;
            }
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            } else {
                this.nextValue = this.lastReturned.next;
                this.index++;
                this.lastReturned = this.nextValue;
                return this.lastReturned.data;
            }
        }
    }
}
