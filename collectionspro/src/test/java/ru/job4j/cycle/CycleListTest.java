package ru.job4j.cycle;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class CycleListTest {
    private CycleList.Node<Integer> first = new CycleList.Node<>(1);
    private CycleList.Node<Integer> two = new CycleList.Node<>(2);
    private CycleList.Node<Integer> third = new CycleList.Node<>(3);
    private CycleList.Node<Integer> four = new CycleList.Node<>(4);
    private CycleList<Integer> list = new CycleList<>();

    @Test
    public void whenNoCycleInListThanReturnFalse() {
        first.next = two;
        two.next = third;
        third.next = four;
        four.next = null;
        assertThat(list.hasCycle(first), is(false));
    }

    @Test
    public void whenHasCycleInMiddleThanReturnTrue() {
        first.next = two;
        two.next = third;
        third.next = first;
        four.next = null;
        assertThat(list.hasCycle(first), is(true));
    }

    @Test
    public void whenCycleByItselfThanReturnTrue() {
        first.next = first;
        two.next = third;
        third.next = four;
        four.next = null;
        assertThat(list.hasCycle(first), is(true));
    }

    @Test
    public void whenFirstNodeIsNullThanReturnFalse() {
        CycleList.Node<Integer> first = null;
        assertThat(list.hasCycle(first), is(false));
    }

}