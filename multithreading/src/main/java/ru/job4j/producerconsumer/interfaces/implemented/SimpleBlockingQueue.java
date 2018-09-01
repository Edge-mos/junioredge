package ru.job4j.producerconsumer.interfaces.implemented;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.producerconsumer.interfaces.SimpleBlock;

import java.util.LinkedList;
import java.util.Queue;
@ThreadSafe
public class SimpleBlockingQueue<T> implements SimpleBlock<T> {
    @GuardedBy("queue")
    private final Queue<T> queue = new LinkedList<>();
    private final int capacity;


    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }


    @Override
    public boolean offer(T obj) {
        synchronized (this.queue) {
            while(this.queue.size() >= this.capacity) {
                try {
                    System.out.println("Заполнение!");
                    this.queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            boolean result = this.queue.add(obj);
            this.queue.notify();
            return result;
        }
    }

    @Override
    public T poll() throws InterruptedException {
        synchronized (this.queue) {
            while (this.queue.isEmpty()) {
                System.out.println("WAITING");
                this.queue.wait();

            }
            T val = this.queue.poll();
            this.queue.notify();
            return val;
        }
    }

    @Override
    public int size() {
        synchronized (this.queue) {
            return this.queue.size();
        }
    }
}
