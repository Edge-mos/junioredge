package ru.job4j.tree.interfaces.implemented;

import ru.job4j.tree.interfaces.SimpleTree;

import java.util.*;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 01.06.2018.
 */

public class Tree<E extends Comparable<E>> implements SimpleTree<E> {
    private  Tree.Node<E> root;

    @Override
    public boolean addNode(E parent, E child) {
        boolean result = false;
        if (this.root == null) {
            this.root = new Tree.Node<>(child);
            result = true;
        } else {
            Optional<Tree.Node<E>> parentExist = this.findBy(parent);
            Optional<Tree.Node<E>> childExist = this.findBy(child);

            if (!childExist.isPresent() && parentExist.isPresent()) {
                Tree.Node<E> parentNode = parentExist.get();
                parentNode.add(new Tree.Node<>(child));
                result = true;
            }
        }
        return result;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> result = Optional.empty();            // Optional.empty - пустой объект.
        Queue<Node<E>> data = new LinkedList<>();               // очередь для элементов.
        data.offer(this.root);                                  // попытка ввода элемента в очередь. Начинает с начального узла.
        while (!data.isEmpty()) {
            Node<E> element = data.poll();                      // poll - возвращает элемент из головы очереди и удаляет его.
            if (element.eqValue(value)) {                       // сравнивает значение родительского узла(сначало) с поступившим значением.
                result = Optional.of(element);                  // Optional.of - объект с ненулевым значением.
                break;
            }                                                   // Если значения различаются, то идёт добавление в очередь всех дочерних элементов
            for (Node<E> child : element.leaves()) {            // из узла в очередь и они добавляются к сравнению.
                if (child != null) {
                    data.offer(child);
                }
            }
        }
        return result;
    }

    @Override
    public boolean isBinary() {
        boolean result = true;
        Queue<Tree.Node<E>> values = this.copy();
        for (Node<E> node : values) {
            if (node.leaves().size() > 2) {
                result = false;
            }
        }
        return result;
    }

    private Queue<Tree.Node<E>> copy() {
        Queue<Node<E>> copied = new PriorityQueue<>(new Comparator<Node<E>>() {
            @Override
            public int compare(Tree.Node<E> o1, Tree.Node<E> o2) {
                return o1.value.compareTo(o2.value);
            }
        });
        if (this.root != null) {
            Queue<Node<E>> data = new LinkedList<>();
            data.offer(this.root);
            while (!data.isEmpty()) {
                Tree.Node<E> element = data.poll();
                copied.offer(element);
                for (Node<E> child : element.leaves()) {
                    if (child != null) {
                        data.offer(child);
                    }
                }
            }
        }
        return copied;
    }

    @Override
    public String toString() {
        return this.copy().toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new TreeIterator();
    }

    public static class Node<E extends Comparable<E>> {
        final E value;
        private final List<Node<E>> children = new ArrayList<>();

        public Node(E value) {
            this.value = value;
        }

        public void add(Node<E> child) {
            this.children.add(child);
        }

        public List<Node<E>> leaves() {
            return this.children;
        }

        public boolean eqValue(E that) {
            return this.value.compareTo(that) == 0;
        }

        @Override
        public String toString() {
            return this.value.toString();

        }
    }

    private class TreeIterator implements Iterator<E> {
        private Queue<Tree.Node<E>> data = Tree.this.copy();

        @Override
        public boolean hasNext() {
            return !this.data.isEmpty();
        }

        @Override
        public E next() {
            return this.data.remove().value;
        }
    }
}
