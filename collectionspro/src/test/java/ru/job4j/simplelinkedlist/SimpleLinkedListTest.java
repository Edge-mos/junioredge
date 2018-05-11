package ru.job4j.simplelinkedlist;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleLinkedListTest {
    private SimpleLinkedList<Integer> list;

    @Before
    public void setup() {
        this.list = new SimpleLinkedList<>();
        list.addFirst(1);
        list.addFirst(2);
        list.addFirst(3);
    }

    @Test
    public void whenAddThanElementAddedToListEnd() {
        list.add(4);
        assertThat(list.get(list.getSize() - 1), is(4));
    }

    @Test
    public void whenAddThreeElementsAndGetElementByIndexOne() {
        assertThat(list.get(1), is(2));

    }
    @Test(expected = NoSuchElementException.class)
    public void whenIndexNotInListShouldThrowNoSuchElementExeption() {
        list.get(3);
    }

    @Test
    public void whenAddTreeElementsThanSizeOfListIsTree() {
        assertThat(list.getSize(), is(3));
    }

    @Test
    public void whenDeleteFirstElementFromListThanGetThisElementAndSizeReduceToOne() {
        assertThat(list.deleteFirst(), is(3));
        assertThat(list.getSize(), is(2));
    }

    @Test
    public void whenDeleteElementByIndexThanGetThisElementAndSizeReduceToOne() {
        assertThat(list.delete(1), is(2));
        assertThat(list.getSize(), is(2));
    }

    @Test
    public void whenDeleteLastElementThanGetThisElementAndSizeReduceToOne() {
        assertThat(list.deleteLast(), is(1));
        assertThat(list.getSize(), is(2));
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorTest() {
        Iterator<Integer> it = list.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(false));
        assertThat(it.next(), is(0));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenChangeListDuringIterationTnanGetExeption() {
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            list.deleteFirst();
            it.next();
        }
    }
}