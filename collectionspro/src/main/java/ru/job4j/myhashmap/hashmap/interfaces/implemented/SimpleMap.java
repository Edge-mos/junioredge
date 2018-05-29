package ru.job4j.myhashmap.hashmap.interfaces.implemented;

import ru.job4j.generics.SimpleArray;
import ru.job4j.myhashmap.hashmap.interfaces.Imap;
import ru.job4j.set.interfaces.implemented.SimpleSet;
import ru.job4j.simplelinkedlist.SimpleLinkedList;

import static java.lang.Math.abs;

import java.util.*;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 25.05.2018.
 */
public class SimpleMap<K, V> implements Imap<K, V> {
    private SimpleMap.Node<K, V>[] table;
    private final int initialCap = 16;
    private int currentCapacity;
    private double loadFactor;
    private int threshold;
    private int size;
    private SimpleSet<K> keyset = new SimpleSet<>();

    public SimpleMap() {
        this.table = new SimpleMap.Node[this.initialCap];
        this.currentCapacity = this.initialCap;
        this.loadFactor = 0.75;
        this.threshold = this.setThreshold();
    }

    public SimpleMap(int currentCapacity) {
        this.currentCapacity = this.checkCurrentCapacity();
        this.table = new SimpleMap.Node[this.currentCapacity];
        this.loadFactor = 0.75;
        this.threshold = this.setThreshold();
    }

    public SimpleMap(int currentCapacity, double loadFactor) {
        this.currentCapacity = this.checkCurrentCapacity();
        this.table = new SimpleMap.Node[this.currentCapacity];
        this.loadFactor = this.checkLoadFactor();
        this.threshold = this.setThreshold();
    }

    @Override
    public boolean insert(K key, V value) {
        if (key != null) {
            SimpleMap.Node<K, V> tmp = new SimpleMap.Node<>(key.hashCode(), key, value);
            int index = this.getTableIndex(tmp);
            this.tableInsert(index, tmp);
            this.thresholdCheck();
            this.keyset.add(key);
            return true;
        }
        return false;
    }

    private void tableInsert(int index, SimpleMap.Node<K, V> node) {
        if (this.table[index] == null) {
            this.table[index] = node;
            this.size++;
            return;
        }
        SimpleMap.Node<K, V> tmp = this.table[index];
        SimpleMap.Node<K, V> last = null;
        while (tmp != null) {
            if (this.isSameKey(tmp, node)) {
                this.swap(tmp, node);
                return;
            } else {
                last = tmp;
                tmp = tmp.next;
            }
        }
        last.next = node;
        this.size++;
    }

    private boolean isSameKey(Object current, Object node) {
        return current.hashCode() == node.hashCode() && current.equals(node);
    }

    private void swap(SimpleMap.Node<K, V> current, SimpleMap.Node<K, V> node) {
        current.hash = node.hash;
        current.value = node.value;
    }

    private int getTableIndex(Object obj) {
        return abs(obj.hashCode()) % this.table.length;
    }

    private void thresholdCheck() {
        if (this.size > this.threshold) {
            this.currentCapacity *= 2;
            SimpleMap.Node<K, V>[] copied = this.copy();
            this.clear();
            for (Node<K, V> oldNode : copied) {
                this.insert(oldNode.key, oldNode.value);
            }
            System.out.println("SIZE INCREASED!!!");
        }
    }

    private int setThreshold() {
        return (int) (this.table.length * this.loadFactor);
    }

    private int checkCurrentCapacity() {
        return (this.currentCapacity < 1) ? (1) : (this.currentCapacity);
    }

    private double checkLoadFactor() {
        return (this.loadFactor < 0) ? (0.50) : (this.loadFactor);
    }

    private  SimpleMap.Node<K, V>[] copy() {
        SimpleMap.Node<K, V>[] oldTable = new SimpleMap.Node[this.size];
        int index = 0;
        for (Node<K, V> node : this.table) {
            if (node != null) {
                while (node != null) {
                    oldTable[index++] = node;
                    node = node.next;
                }
            }
        }
        return oldTable;
    }

    @Override
    public V get(K key) {
        if (key != null) {
            int index = this.getTableIndex(key);
            SimpleMap.Node<K, V> tmp = this.table[index];
            while (tmp != null) {
                if (this.isSameKey(tmp.key, key)) {
                    return tmp.value;
                } else {
                    tmp = tmp.next;
                }
            }
        }
        return null;
    }

    @Override
    public boolean delete(K key) {
        int index = this.getTableIndex(key);
        SimpleMap.Node<K, V> tmp = this.table[index];
        SimpleMap.Node<K, V> last = tmp;
        if (tmp != null) {
            while (tmp != null) {
                if (this.isSameKey(tmp.key, key)) {
                    break;
                } else {
                    last = tmp;
                    tmp = tmp.next;
                }
            }
            if (tmp != null) {
                if (tmp == last) {
                    this.table[index] = last.next;
                    tmp = null;
                    this.size--;
                } else {
                    last.next = last.next.next;
                    tmp = null;
                    this.size--;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public void clear() {
        if (!this.isEmpty()) {
            this.table = new SimpleMap.Node[this.currentCapacity];
            this.threshold = this.setThreshold();
            this.size = 0;
        }
    }

    @Override
    public SimpleSet<K> keySet() {
        return this.keyset;
    }

    @Override
    public SimpleLinkedList<V> values() {
        SimpleLinkedList<V> values = new SimpleLinkedList<>();
        for (Node<K, V> node : this.table) {
            if (node != null) {
                values.add(node.value);
            }
        }
        return values;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.copy());
    }

    @Override
    public Iterator iterator() {
        return new GetIterator();

    }

    public static class Node<K, V> {
        private int hash;
        private K key;
        private V value;
        private Node<K, V> next;

        public Node(int hash, K key, V value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }

            Node<?, ?> node = (Node<?, ?>) o;
            if (Objects.equals(this.key, node.key)) {
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public String toString() {
            return key + "->" + value;
        }
    }


    private class GetIterator  implements Iterator<SimpleMap.Node<K, V>> {
        private SimpleMap.Node<K, V>[] values;
        private int tableIndex;
        private SimpleMap.Node<K, V> cursorNode;
        private SimpleMap.Node<K, V> temp;
        private int expectedModCount;

        public GetIterator() {
            this.values = SimpleMap.this.table;
            this.tableIndex = 0;
            this.cursorNode = this.search();
            this.expectedModCount = SimpleMap.this.size;
        }

        @Override
        public boolean hasNext() {
            return this.tableIndex < this.values.length - 1 && !SimpleMap.this.isEmpty();
        }

        @Override
        public Node<K, V> next() {
            this.checkForModification();
            if (this.hasNext()) {
                if (this.cursorNode.next == null) {
                    temp = this.cursorNode;
                    this.tableIndex++;
                    this.cursorNode = this.search();
                    return temp;
                } else {
                    temp = this.cursorNode;
                    this.cursorNode = cursorNode.next;
                    return temp;
                }
            }
            throw new NoSuchElementException();
        }

        private SimpleMap.Node<K, V> search() {
            for (int i = this.tableIndex; i < this.values.length; i++) {
                if (this.values[i] != null) {
                    this.tableIndex = i;
                    break;
                }
                this.tableIndex = i;
            }
            return this.values[this.tableIndex];
        }

        final void checkForModification() {
            if (SimpleMap.this.size != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
