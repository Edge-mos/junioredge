package ru.job4j.generics;

import org.junit.Test;
import ru.job4j.generics.SimpleArray;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleArrayTest {

    @Test
    public void whenCreateContainerOfStringShoudReturnStringType() {
        SimpleArray<String> sa = new SimpleArray<>(1);
        sa.add("test");
        String result = sa.get(0);
        assertThat(result, is("test"));
    }

    @Test
    public void whenCreateContainerOfIntegerShoudReturnIntegerType() {
        SimpleArray<Integer> sa = new SimpleArray<>(1);
        sa.add(1);
        int result = sa.get(0);
        assertThat(result, is(1));
    }

    @Test
    public void whenNotEnoughCapasityContainerShoudIncreaseSize() {
        SimpleArray<Integer> sa = new SimpleArray<>(1);
        sa.add(1);
        sa.add(2);
        sa.add(3);
        sa.add(4);
        sa.add(5);
        sa.add(6);
        sa.add(7);
        sa.add(8);
        sa.add(9);
        assertThat(sa.size(), is(9));
    }

    @Test
    public void whenSetItemByIndexThanItemReplace() {
        SimpleArray<Integer> sa = new SimpleArray<>(5);
        sa.add(1);
        sa.add(2);
        sa.add(3);
        sa.set(1, 555);
        assertThat(sa.get(1), is(555));
    }

    @Test
    public void whenDeleteItemThanOtherElementsDecrementTheirIndexes() {
        SimpleArray<String> sa = new SimpleArray<>(1);
        sa.add("zero");
        sa.add("one");
        sa.add("two");
        sa.delete(1);
        assertThat(sa.get(1), is("two"));
    }

    @Test
    public void test() {
        SimpleArray<String> sa = new SimpleArray<>(1);
        sa.add("zero");
        sa.add("one");
        sa.add("two");
        sa.delete(0);
    }

    @Test
    public void testingIterator() {
        SimpleArray<String> sa = new SimpleArray<>(3);
        sa.add("one");
        sa.add("two");
        sa.add("three");
        Iterator it = sa.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("one"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("two"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is("three"));
        assertThat(it.hasNext(), is(false));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenChangeListDuringIterationTnanGetExeption() {
        SimpleArray<String> sa = new SimpleArray<>(3);
        sa.add("one");
        sa.add("two");
        sa.add("three");
        Iterator it = sa.iterator();
        for (String s : sa) {
            sa.add(s);
        }
    }
}