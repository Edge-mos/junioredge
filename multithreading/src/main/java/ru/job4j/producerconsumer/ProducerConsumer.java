package ru.job4j.producerconsumer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class ProducerConsumer {
    @GuardedBy("lock")
    private Queue<Integer> queue = new LinkedList<>();
    private final Object lock = new Object();
    private final int limit = 10;



    public void producer(int iterations) throws InterruptedException {
        for (int i = 0; i < iterations; i++) {
            synchronized (lock) {
                while (this.queue.size() == this.limit) {
                    System.out.println("Заполнение!");
                    this.lock.wait();
                }
                this.queue.offer(i);
                this.lock.notify();
            }

        }
    }

    public int[] consumer(int iterations) throws InterruptedException {
        int[] result = new int[iterations];
        int index = 0;
        for (int i = 0; i < iterations; i++) {
            synchronized (lock) {
                while (this.queue.size() == 0) {
                    System.out.println("Очередь пуста");
                    this.lock.wait();
                }
                int value = this.queue.poll();
                System.out.println(value);
                System.out.println(String.format("size = %d", this.queue.size()));
                result[index++] = value;
                this.lock.notify();
            }
            //Thread.sleep(1000);
        }
        return result;
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
