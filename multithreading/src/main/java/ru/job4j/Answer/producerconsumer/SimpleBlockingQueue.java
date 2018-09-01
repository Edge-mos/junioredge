package ru.job4j.Answer.producerconsumer;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleBlockingQueue<T> {
    /**
     * Maximum number of elements in the inner queue,.
     */
    private static final int MAX_QUEUE_SIZE = 4;
    /**
     * Inner queue containing elements.
     */
    private final Queue<T> queue = new LinkedList<>();

    /**
     * Offers new element to the queue.
     *
     * @param value element to add.
     * @throws InterruptedException if interrupted while waiting.
     */
    void offer(T value) throws InterruptedException {
        synchronized (this.queue) {
            while (this.queue.size() >= MAX_QUEUE_SIZE) {
                System.out.format("%s: waiting offer, queue size: %s%n", Thread.currentThread().getId(), this.queue.size());
                this.queue.wait();
            }
            System.out.format("%s: OFFER \"%s\"%n", Thread.currentThread().getId(), value);
            this.queue.offer(value);
            this.queue.notify();
        }
    }

    /**
     * Returns next element and removes it from the queue.
     *
     * @return next element in the queue.
     * @throws InterruptedException if interrupted while waiting.
     */
    T poll() throws InterruptedException {
        synchronized (this.queue) {
            while (this.queue.isEmpty()) {
                System.out.format("%s: waiting poll, queue size: %s%n", Thread.currentThread().getId(), this.queue.size());
                this.queue.wait();
            }
            T result = this.queue.poll();
            System.out.format("%s: POLL \"%s\"%n", Thread.currentThread().getId(), result);
            this.queue.notify();
            return result;
        }
    }
}
