package ru.job4j.threadpool;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class ThreadPoolTest {
    private int count = 0;

    private class Work implements Runnable {
        private int id;

        public Work(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println("Work " + id + " was completed");
        }
    }

    private class IncrementCounter implements Runnable {

        @Override
        public void run() {
            this.increment();
        }

        private void increment() {
            ThreadPoolTest.this.count++;
        }
    }

    @Test
    public void whenDoSimpleWork() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();

        for (int i = 0; i < 50; i++) {
            threadPool.addWork(new Work(i));
        }

        threadPool.shutdown();
        System.out.println("All tasks was completed");

    }

    @Test
    public void when50IncrementsThreadDoneThanResult50() throws InterruptedException {
        ThreadPool threadPool = new ThreadPool();
        for (int i = 0; i < 50; i++) {
            threadPool.addWork(new IncrementCounter());
        }

        threadPool.shutdown();
        System.out.println("All tasks was completed");
        assertThat(this.count, is(50));

    }

}