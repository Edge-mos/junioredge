package ru.job4j.simplecontainersstackqueue.interfaces;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.simplecontainersstackqueue.containers.SimpleQueue;
import ru.job4j.simplecontainersstackqueue.containers.SimpleStack;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StorableTest {
    private Storable<Integer> stack;
    private Storable<Integer> queue;

    @Before
    public void setup() {
        this.stack = new SimpleStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        this.queue = new SimpleQueue<>();
        queue.push(4);
        queue.push(5);
        queue.push(6);
    }

    @Test
    public void whenPollElementsThatGetThisElementsAndDeleteFromStack() {
        assertThat(stack.poll(), is(3));
        assertThat(stack.poll(), is(2));
        assertThat(stack.poll(), is(1));
        assertThat(stack.size(), is(0));

        assertThat(queue.poll(), is(4));
        assertThat(queue.poll(), is(5));
        assertThat(queue.poll(), is(6));
        assertThat(queue.size(), is(0));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNoElementsInStackTnanGetExeption() {
        stack.poll();
        stack.poll();
        stack.poll();
        stack.poll();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenNoElementsInQueueTnanGetExeption() {
        queue.poll();
        queue.poll();
        queue.poll();
        queue.poll();
    }

}