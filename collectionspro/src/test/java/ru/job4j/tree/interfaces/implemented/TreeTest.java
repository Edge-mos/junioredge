package ru.job4j.tree.interfaces.implemented;

import org.junit.Test;
import ru.job4j.tree.interfaces.SimpleTree;

import java.io.OptionalDataException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static java.lang.Math.abs;
import static org.junit.Assert.*;


public class TreeTest {

    @Test
    public void whenAddUniqueElementThanTrue() {
        SimpleTree<Integer> tree = new Tree<>();
        assertThat(tree.addNode(1, 37), is(true));
        assertThat(tree.addNode(37, 37), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorTest() {
        SimpleTree<Integer> tree = new Tree<>();
        tree.addNode(37, 37);
        tree.addNode(37, 1);
        tree.addNode(37, 2);
        tree.addNode(37, 3);
        tree.addNode(1, 24);

        Iterator<Integer> it = tree.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(24));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(37));
        it.next();
    }

    @Test
    public void isBinaryFalse() {
        SimpleTree<Integer> tree = new Tree<>();
        tree.addNode(37, 37);
        tree.addNode(37, 1);
        tree.addNode(37, 2);
        tree.addNode(37, 3);
        tree.addNode(1, 24);
        assertThat(tree.isBinary(), is(false));
    }

    @Test
    public void isBinaryTrue() {
        SimpleTree<Integer> tree = new Tree<>();
        tree.addNode(37, 37);
        tree.addNode(37, 1);
        tree.addNode(1, 23);
        tree.addNode(1, 24);
        tree.addNode(37, 2);
        tree.addNode(2, 3);
        assertThat(tree.isBinary(), is(true));
    }

}