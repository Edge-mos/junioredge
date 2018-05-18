package ru.job4j.set;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.set.interfaces.Iset;
import ru.job4j.set.interfaces.implemented.LinkedSet;
import ru.job4j.set.interfaces.implemented.SimpleSet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Iterator;

public class SimpleSetTest {
    private Iterator<Integer> sit;
    private Iterator<String> lit;

    @Before
    public void setup() {
        Iset<Integer> simpleSet = new SimpleSet<>();
        Iset<String> linkedSet = new LinkedSet<>();
        simpleSet.add(1);
        simpleSet.add(2);
        simpleSet.add(1);
        simpleSet.add(3);
        simpleSet.add(2);
        simpleSet.add(11);
        linkedSet.add("One");
        linkedSet.add("Two");
        linkedSet.add("One");
        linkedSet.add("Two");
        linkedSet.add("Three");
        sit = simpleSet.iterator();
        lit = linkedSet.iterator();
    }

    @Test
    public void whenAddSameValuesInSetThanGetUniqueValues() {
        assertThat(sit.hasNext(), is(true));
        assertThat(sit.next(), is(1));
        assertThat(sit.hasNext(), is(true));
        assertThat(sit.next(), is(2));
        assertThat(sit.hasNext(), is(true));
        assertThat(sit.next(), is(3));
        assertThat(sit.hasNext(), is(true));
        assertThat(sit.next(), is(11));
        assertThat(sit.hasNext(), is(false));

        assertThat(lit.hasNext(), is(true));
        assertThat(lit.next(), is("One"));
        assertThat(lit.hasNext(), is(true));
        assertThat(lit.next(), is("Two"));
        assertThat(lit.hasNext(), is(true));
        assertThat(lit.next(), is("Three"));
        assertThat(lit.hasNext(), is(false));
    }

}