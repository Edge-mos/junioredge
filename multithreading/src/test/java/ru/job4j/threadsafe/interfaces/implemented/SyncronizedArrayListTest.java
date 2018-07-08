package ru.job4j.threadsafe.interfaces.implemented;

import org.junit.Test;
import ru.job4j.threadsafe.interfaces.SyncList;

import java.util.Iterator;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import static org.junit.Assert.*;

public class SyncronizedArrayListTest {

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



    @Test
    public void whenAddIn2Threads() throws InterruptedException {
        final SyncList<Integer> list = new SyncronizedArrayList<>(2);
        Thread thread1 = new ThreadAdd(list, 1, 2);
        Thread thread2 = new ThreadAdd(list, 3, 4, 5);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println(list);

        assertThat(list.size(), is(5));
        assertThat(list.get(2), is(3));
    }

}