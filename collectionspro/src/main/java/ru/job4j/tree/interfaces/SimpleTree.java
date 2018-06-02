package ru.job4j.tree.interfaces;

import ru.job4j.tree.interfaces.implemented.Tree;

import java.util.Optional;
import java.util.Queue;

public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {
    boolean addNode(E parent, E child);
    Optional<Tree.Node<E>> findBy(E value);
    boolean isBinary();
}
