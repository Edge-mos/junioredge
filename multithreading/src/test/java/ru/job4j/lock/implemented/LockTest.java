package ru.job4j.lock.implemented;

import org.junit.Test;
import org.junit.runner.notification.RunListener;
import ru.job4j.lock.interfaces.SimpleLock;
import ru.job4j.threadpool.ThreadPool;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

@RunListener.ThreadSafe
public class LockTest {
    private int count = 0;
    private final SimpleLock lock = new Lock();
    private final ThreadPool threadPool = new ThreadPool();

    private void increment() throws InterruptedException {
        this.lock.lock();
        this.count++;
        this.lock.unlock();
    }

    private void decrement() throws InterruptedException {
        this.lock.lock();
        this.count--;
        this.lock.unlock();
    }

    private final class ThreadIncrement extends Thread {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 100; i++) {
                    LockTest.this.increment();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private final class ThreadDecrement extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                try {
                    LockTest.this.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void whenIncrementIn20ThreadsThanShould2000() throws InterruptedException {

        for (int i = 0; i < 20; i++) {
            this.threadPool.addWork(new ThreadIncrement());
        }

        this.threadPool.shutdown();
        assertThat(this.count, is(2000));

    }

    @Test
    public void whenIncrementIn15ThreadsAndDecrementIn5ThreadsThanShould1000() throws InterruptedException {

        for (int i = 0; i < 20; i++) {
            if (i < 15) {
                this.threadPool.addWork(new ThreadIncrement());
                continue;
            }
            this.threadPool.addWork(new ThreadDecrement());
        }

        this.threadPool.shutdown();
        assertThat(this.count, is(1000));
    }

}