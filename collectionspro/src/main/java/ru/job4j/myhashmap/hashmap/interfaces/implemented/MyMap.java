package ru.job4j.myhashmap.hashmap.interfaces.implemented;

import ru.job4j.myhashmap.hashmap.interfaces.SimpleMap;
import static java.lang.Math.abs;

import java.util.*;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 25.05.2018.
 */
public class MyMap<K, V> implements SimpleMap<K, V> {
    private MyMap.Node<K, V>[] table;
    private final int initialCap = 16;
    private int currentCapacity;
    private double loadFactor;
    private int threshold;
    private int size;
    private int modCount;

    public MyMap() {
        this.table = new MyMap.Node[this.initialCap];
        this.currentCapacity = this.initialCap;
        this.loadFactor = 0.75;
        this.threshold = this.setThreshold();
    }

    public MyMap(int currentCapacity) {
        this.currentCapacity = this.checkCurrentCapacity();
        this.table = new MyMap.Node[this.currentCapacity];
        this.loadFactor = 0.75;
        this.threshold = this.setThreshold();
    }

    public MyMap(int currentCapacity, double loadFactor) {
        this.currentCapacity = this.checkCurrentCapacity();
        this.table = new MyMap.Node[this.currentCapacity];
        this.loadFactor = this.checkLoadFactor();
        this.threshold = this.setThreshold();
    }

    @Override
    public boolean insert(K key, V value) {
        if (key != null) {
            MyMap.Node<K, V> tmp = new MyMap.Node<>(key.hashCode(), key, value);
            int index = this.getTableIndex(tmp);
            this.tableInsert(index, tmp);
            this.thresholdCheck();
            return true;
        }
        return false;
    }

    private void tableInsert(int index, MyMap.Node<K, V> node) {
        if (this.table[index] == null) {
            this.table[index] = node;
            this.size++;
            ++this.modCount;
            return;
        }
        MyMap.Node<K, V> tmp = this.table[index];
        MyMap.Node<K, V> last = null;
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
        ++this.modCount;
    }

    private boolean isSameKey(Object current, Object node) {
        return current.hashCode() == node.hashCode() && current.equals(node);
    }

    private void swap(MyMap.Node<K, V> current, MyMap.Node<K, V> node) {
        current.hash = node.hash;
        current.value = node.value;
    }

    private int getTableIndex(Object obj) {
        return abs(obj.hashCode()) % this.table.length;
    }

    private void thresholdCheck() {
        if (this.size > this.threshold) {
            this.currentCapacity *= 2;
            MyMap.Node<K, V>[] copied = this.copy();
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

    private  MyMap.Node<K, V>[] copy() {
        MyMap.Node<K, V>[] oldTable = new MyMap.Node[this.size];
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
            MyMap.Node<K, V> tmp = this.table[index];
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
        MyMap.Node<K, V> tmp = this.table[index];
        MyMap.Node<K, V> last = tmp;
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
                    ++this.modCount;
                } else {
                    last.next = last.next.next;
                    tmp = null;
                    this.size--;
                    ++this.modCount;
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
            this.table = new MyMap.Node[this.currentCapacity];
            this.threshold = this.setThreshold();
            this.size = 0;
            this.modCount = 0;
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(this.copy());
    }

    @Override
    public Iterator iterator() {
        return new GetIterator();
    }

    private static class Node<K, V> {
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
    private class GetIterator implements Iterator<MyMap.Node<K, V>> {
        private MyMap.Node<K, V>[] values;
        private int tableIndex;
        private MyMap.Node<K, V> cursorNode;
        private MyMap.Node<K, V> currentNode;
        private MyMap.Node<K, V> temp;
        private int expectedModCount;

        public GetIterator() {
            this.values = MyMap.this.table;
            this.tableIndex = 0;
            this.cursorNode = this.search();
            this.currentNode = this.cursorNode;
            this.expectedModCount = MyMap.this.modCount;
        }

        @Override
        public boolean hasNext() {
            return this.tableIndex < this.values.length;
        }

        @Override
        public Node<K, V> next() {
            this.checkForModification();
            if (this.hasNext()) {
                if (cursorNode.next == null) {
                    temp = currentNode;
                    cursorNode = this.search();
                    currentNode = cursorNode;
                    this.tableIndex++;
                    return temp;
                } else {
                    cursorNode = cursorNode.next;
                    temp = currentNode;
                    currentNode = cursorNode;
                    this.tableIndex++;
                    return temp;
                }
            }
            throw new NoSuchElementException();
        }

        private MyMap.Node<K, V> search() {
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
            if (MyMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
