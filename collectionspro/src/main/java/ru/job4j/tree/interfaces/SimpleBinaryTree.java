package ru.job4j.tree.interfaces;

import ru.job4j.tree.interfaces.implemented.BinaryTree;

import java.util.Queue;

public interface SimpleBinaryTree<E extends Comparable<E>> extends Iterable<E> {
    void add(E data);
    void recAdd(E data);
    BinaryTree.Node<E> find(E data);
    int size();
}
