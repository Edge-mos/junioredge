package ru.job4j.threadsafe.interfaces.implemented;

import org.junit.Test;
import ru.job4j.threadsafe.interfaces.SyncList;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SyncronizedLinkedListTest {

    private class ThreadAdd extends Thread {
        private final SyncList<Integer> list;
        private final int[] ints;

        public ThreadAdd(SyncList<Integer> list, int...ints) {
            this.list = list;
            this.ints = ints;
        }

        @Override
        public void run() {
            for (int anInt : ints) {
                this.list.add(anInt);
            }
        }
    }

    private class ThreadDelete extends Thread {
        private final SyncList<Integer> list;
        private final int[] indexes;

        public ThreadDelete(SyncList<Integer> list, int...indexes) {
            this.list = list;
            this.indexes = indexes;
        }

        @Override
        public void run() {
            for (int index : indexes) {
                this.list.delete(index);
            }
        }
    }

    @Test
    public void whenAddIn2Threads() throws InterruptedException {
        final SyncList<Integer> list = new SyncronizedLinkedList<>();
        Thread thread1 = new ThreadAdd(list, 1, 2);
        Thread thread2 = new ThreadAdd(list, 3, 4, 5);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        List<Integer> sortedList = new ArrayList<>();
        for (Integer integer : list) {
            sortedList.add(integer);
        }

        sortedList.sort(Comparator.naturalOrder());
        for (int i = 0; i < 5; i++) {
            assertThat(sortedList.get(i), is(i + 1));
        }

        assertThat(list.size(), is(5));
    }

    @Test
    public void whenAddAndDeleteIn2Threads() throws InterruptedException {
        final SyncList<Integer> list = new SyncronizedLinkedList<>();
        Thread thread1 = new ThreadAdd(list, 1, 2, 3, 4, 5);
        Thread thread2 = new ThreadDelete(list, 3, 2, 1, 0);
        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();

        assertThat(list.size(), is(1));
        assertThat(list.get(0), is(5));
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorTest() throws InterruptedException {
        final SyncList<Integer> list = new SyncronizedLinkedList<>();
        Thread thread1 = new ThreadAdd(list, 1, 2, 3, 4, 5);
        thread1.start();
        thread1.join();

        Iterator<Integer> it = list.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(4));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(5));
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenConcurrentModificationExeptionOccured() throws InterruptedException {
        final SyncList<Integer> list = new SyncronizedLinkedList<>();
        Thread thread1 = new ThreadAdd(list, 1, 2, 3, 4, 5);
        thread1.start();
        thread1.join();
        for (Integer integer : list) {
            list.delete(1);
        }
    }
}