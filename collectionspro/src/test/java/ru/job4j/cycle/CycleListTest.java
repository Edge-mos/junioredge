package ru.job4j.cycle;

import org.junit.Test;

import static org.junit.Assert.*;

public class CycleListTest {

    @Test
    public void test() {
        CycleList.Node<Integer> first = new CycleList.Node<>(1);
        CycleList.Node<Integer> two = new CycleList.Node<>(2);
        CycleList.Node<Integer> third = new CycleList.Node<>(3);
        CycleList.Node<Integer> four = new CycleList.Node<>(4);

        CycleList<Integer> list = new CycleList<>();

        first.next = two;
        two.next = third;
        third.next = four;
        four.next = first;

        System.out.println(list.hasCycle(first));
    }

}