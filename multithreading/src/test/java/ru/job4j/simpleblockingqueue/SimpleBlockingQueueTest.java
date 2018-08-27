package ru.job4j.simpleblockingqueue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class SimpleBlockingQueueTest {

    private class Producer<T> extends Thread {
        private final SimpleBlockingQueue<T> queue;
        private ArrayList<T> values;

        public Producer(SimpleBlockingQueue<T> queue, ArrayList<T> values) {
            this.queue = queue;
            this.values = values;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void run() {
            for (T value : values) {
                this.queue.offer(value);
            }
        }
    }

    private class Consumer<T> extends Thread {
        private final SimpleBlockingQueue<T> queue;
        private T value;
        private ArrayList<T> result = new ArrayList<>();

        public Consumer(SimpleBlockingQueue<T> queue) {
            this.queue = queue;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    this.value = this.queue.poll();
                    if (this.value == null) {
                        Thread.currentThread().interrupt();
                        //break;
                    }
                    this.result.add(this.value);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }

        ArrayList<T> getResult() {
            return this.result;
        }
    }

    @Test
    public void test() throws InterruptedException {
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        final ArrayList<Integer> values = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Consumer<Integer> consumer = new Consumer<>(queue);
        Producer<Integer> producer = new Producer<>(queue, values);



        consumer.start();
        producer.start();
        producer.join();

        consumer.join();

        System.out.println(queue);
        System.out.println("Size After: " + queue.size());
        System.out.println("Consumer result: " + consumer.getResult());


        assertThat(queue.size(), is(0));
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        assertThat(consumer.getResult(), is(expected));



    }

}