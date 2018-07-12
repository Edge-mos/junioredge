package ru.job4j.simpleblockingqueue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("lock")
    private Queue<T> queue = new LinkedList<>();
    private final Object lock = new Object();

    public void offer(T value) {
        synchronized (lock) {
            this.queue.offer(value);
            this.lock.notify();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (lock) {
            while (this.queue.size() == 0) {
                System.out.println("Wait");
                this.lock.wait();
            }
            return this.queue.poll();
        }
    }

    @Override
    public String toString() {
        synchronized (lock) {
            return this.queue.toString();
        }
    }

    public int size() {
        synchronized (lock) {
            return this.queue.size();
        }
    }
}
