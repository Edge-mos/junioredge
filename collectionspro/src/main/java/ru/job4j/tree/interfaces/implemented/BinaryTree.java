package ru.job4j.tree.interfaces.implemented;

import ru.job4j.tree.interfaces.SimpleBinaryTree;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 08.06.2018.
 */

public class BinaryTree<E extends Comparable<E>> implements SimpleBinaryTree<E> {
    private BinaryTree.Node<E> root;
    private int size;

    /**
     * Нерекурсивный метод добавления элемента.
     * @param data обобщённый параметр.
     */
    @Override
    public void add(E data) {
        BinaryTree.Node<E> tmp = new Node<>(data);
        if (this.root == null) {
            this.root = tmp;
            this.size++;
        } else {
            if (this.find(data) == null) {
                BinaryTree.Node<E> current = this.root;
                BinaryTree.Node<E> parent;
                while (true) {
                    parent = current;
                    if (tmp.compareData(current) < 0) {
                        current = current.left;
                        if (current == null) {
                            parent.left = tmp;
                            this.size++;
                            break;
                        }
                    } else {
                        current = current.right;
                        if (current == null) {
                            parent.right = tmp;
                            this.size++;
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void recAdd(E data) {
        if (this.root == null) {
            this.root = new BinaryTree.Node<>(data);
            this.size++;
        } else {
            if (this.find(data) == null) {
                BinaryTree.Node<E> tmp = new Node<>(data);
                this.insertByRecursion(this.root, tmp);
            }
        }
    }

    /**
     *
     * Рекурсивный метод добавления элемента. Используется в связке с методом recAdd, который проверяет наличие корневого елемента
     * и дублирующих значений, если условия удовлетворяются, то подставляет параметр root, как начальный.
     * @param root корневой элемент.
     * @param tmp добавляемый элемент.
     */
    private void insertByRecursion(BinaryTree.Node<E> root, BinaryTree.Node<E> tmp) {
        if (tmp.compareData(root) < 0) {
            if (root.left == null) {
                root.left = tmp;
                this.size++;
            } else {
                insertByRecursion(root.left, tmp);
            }
        } else {
            if (root.right == null) {
                root.right = tmp;
                this.size++;
            } else {
                insertByRecursion(root.right, tmp);
            }
        }
    }

    /**
     * Метод для копирования всех узлов дерева, основан на алгоритме поиска в ширину.
     * @return очередь с элементами дерева.
     */
    private Queue<BinaryTree.Node<E>> copy() {
        Queue<BinaryTree.Node<E>> data = new LinkedList<>();
        Queue<BinaryTree.Node<E>> copied = new LinkedList<>();
        if (this.root != null) {
            BinaryTree.Node<E> current;
            data.offer(this.root);
            while (!data.isEmpty()) {
                current = data.poll();
                copied.offer(current);
                if (current.left != null) {
                    data.offer(current.left);
                }
                if (current.right != null) {
                    data.offer(current.right);
                }
            }
        }
        return copied;
    }

    @Override
    public String toString() {
//        return this.copy().toString();
        return this.recCopy().toString();
    }

    /**
     * метод поиска элемента.
     * @param data обобщённое значение для поиска.
     * @return либо значение, либо null, в случае отсутствие такового.
     */
    @Override
    public Node<E> find(E data) {
        BinaryTree.Node<E> tmp = new Node<>(data);
        BinaryTree.Node<E> current = this.root;
        if (this.root != null) {
            while (tmp.compareData(current) != 0) {
                if (tmp.compareData(current) < 0) {
                    current = current.left;
                } else {
                    current = current.right;
                }
                if (current == null) {
                    return null;
                }
            }
        }
        return current;
    }

    public Queue<BinaryTree.Node<E>> recCopy() {
        Queue<BinaryTree.Node<E>> tmp = new LinkedList<>();
        return this.inOrderTraversal(this.root, tmp);
    }

    /**
     * Рекурсивный проход и копирование данных, используется в связке с методом recCopy(), использует проход узлов в порядке возрастания.
     * Используется в связке с методом recCopy(), который который образует объект очереди и передаёт в метод значение корня дерева и объект очереди.
     * Используется для отработки рекурсии!!!
     * @param localRoot корень дерева.
     * @param queue объект очереди.
     * @return очередь с узлами дерева, в порядке возрастания.
     */
    private Queue<BinaryTree.Node<E>> inOrderTraversal(BinaryTree.Node<E> localRoot, Queue<BinaryTree.Node<E>> queue) {
        if (localRoot != null) {
            inOrderTraversal(localRoot.left, queue);
            queue.offer(localRoot);
            inOrderTraversal(localRoot.right, queue);
        }
        return queue;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Iterator<E> iterator() {
        return new IteratorTree();
    }

    public static class Node<E extends Comparable<E>> {
        private E data;
        private Node<E> left;
        private Node<E> right;

        public int compareData(BinaryTree.Node<E> that) {
            return this.data.compareTo(that.data);
        }

        public Node(E data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Node{"
                    +
                    "data="
                    +
                    data.toString()
                    +
                    '}';
        }
    }

    private class IteratorTree implements Iterator<E> {
//        private Queue<BinaryTree.Node<E>> treeData = BinaryTree.this.copy();
        private Queue<BinaryTree.Node<E>> treeData = BinaryTree.this.recCopy();  // для рекурсии.
        private int modCount = BinaryTree.this.size();

        @Override
        public boolean hasNext() {
            return !treeData.isEmpty();
        }

        @Override
        public E next() {
            this.checkForModification();
            return treeData.remove().data;
        }

        private void checkForModification() {
            if (this.modCount != BinaryTree.this.size) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
