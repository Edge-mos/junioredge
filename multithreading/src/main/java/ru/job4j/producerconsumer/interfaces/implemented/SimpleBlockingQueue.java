package ru.job4j.producerconsumer.interfaces.implemented;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.producerconsumer.interfaces.SimpleBlock;

import java.util.LinkedList;
import java.util.Queue;
@ThreadSafe
public class SimpleBlockingQueue<T> implements SimpleBlock<T> {
    @GuardedBy("lock")
    private final Queue<T> queue = new LinkedList<>();
    private int capacity;
    private final Object lock = new Object();

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }


    @Override
    public boolean offer(T obj) {
        synchronized (this.lock) {
            while(this.queue.size() == this.capacity) {
                try {
                    System.out.println("Заполнение!");
                    this.lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            boolean result = this.queue.add(obj);
            this.lock.notify();
            return result;
        }
    }

    @Override
    public T poll() {
        synchronized (this.lock) {
            while (this.queue.size() == 0) {
                try {
                    System.out.println("WAITING");
                    this.lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T val = this.queue.poll();
            this.lock.notify();
            return val;
        }
    }

    @Override
    public int size() {
        synchronized (this.lock) {
            return this.queue.size();
        }
    }
}
