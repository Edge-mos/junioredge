package ru.job4j.tree.interfaces.implemented;

import org.junit.Test;
import ru.job4j.tree.interfaces.SimpleBinaryTree;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static java.lang.Math.abs;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import static org.junit.Assert.*;

public class BinaryTreeTest {
    @Test
    public void whenNonRecursiveAddThenFindElement() {
        SimpleBinaryTree<Integer> bst = new BinaryTree<>();
        bst.add(33);
        bst.add(22);
        bst.add(20);
        bst.add(44);

        assertThat(bst.find(33).compareData(new BinaryTree.Node<>(33)), is(0));
        assertThat(bst.find(22).compareData(new BinaryTree.Node<>(22)), is(0));
        assertThat(bst.find(20).compareData(new BinaryTree.Node<>(20)), is(0));
        assertThat(bst.find(44).compareData(new BinaryTree.Node<>(44)), is(0));
        assertThat(bst.find(55), is(nullValue()));
    }

    @Test
    public void whenRecursiveAddDataThenFindElement() {
        SimpleBinaryTree<Integer> bst = new BinaryTree<>();
        bst.recAdd(33);
        bst.recAdd(22);
        bst.recAdd(20);
        bst.recAdd(44);

        assertThat(bst.find(33).compareData(new BinaryTree.Node<>(33)), is(0));
        assertThat(bst.find(22).compareData(new BinaryTree.Node<>(22)), is(0));
        assertThat(bst.find(20).compareData(new BinaryTree.Node<>(20)), is(0));
        assertThat(bst.find(44).compareData(new BinaryTree.Node<>(44)), is(0));
        assertThat(bst.find(55), is(nullValue()));
    }

    @Test
    public void whenAddSameElementThenAddOnlyUniqueElement() {
        SimpleBinaryTree<Integer> bst = new BinaryTree<>();
        bst.recAdd(33);
        assertThat(bst.find(33).compareData(new BinaryTree.Node<>(33)), is(0));
        assertThat(bst.size(), is(1));
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorTestInOrder() {
        SimpleBinaryTree<Integer> bst = new BinaryTree<>();
        bst.recAdd(33);
        bst.recAdd(22);
        bst.recAdd(20);
        bst.recAdd(44);
        Iterator<Integer> it = bst.iterator();

        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(20));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(22));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(33));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(44));
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void concurrentTest() {
        SimpleBinaryTree<Integer> bst = new BinaryTree<>();
        bst.recAdd(33);
        bst.recAdd(22);
        bst.recAdd(20);
        bst.recAdd(44);
        Iterator<Integer> it = bst.iterator();
        while (it.hasNext()) {
            it.next();
            bst.recAdd(55);
        }

    }

}