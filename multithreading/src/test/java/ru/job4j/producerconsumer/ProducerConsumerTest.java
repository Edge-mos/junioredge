package ru.job4j.producerconsumer;

import org.junit.Test;

public class ProducerConsumerTest {

    private class ThreadProducer extends Thread {
        private final ProducerConsumer pc;

        public ThreadProducer(ProducerConsumer pc) {
            this.pc = pc;
        }

        @Override
        public void run() {
            try {
                this.pc.producer(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private class ThreadConsumer extends Thread {
        private final ProducerConsumer pc;

        public ThreadConsumer(ProducerConsumer pc) {
            this.pc = pc;
        }

        @Override
        public void run() {
            try {
                this.pc.consumer(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test() throws InterruptedException {
        final ProducerConsumer pc = new ProducerConsumer();
        Thread producer = new ThreadProducer(pc);
        Thread producer2 = new ThreadProducer(pc);
        Thread consumer = new ThreadConsumer(pc);
        Thread consumer2 = new ThreadConsumer(pc);

        producer.start();
        consumer.start();
        consumer2.start();
        producer2.start();

        producer.join();
        consumer.join();
        consumer2.join();
        producer2.join();
        System.out.println(pc);
        System.out.println(String.format("Size: %d", pc.size()));
    }

}