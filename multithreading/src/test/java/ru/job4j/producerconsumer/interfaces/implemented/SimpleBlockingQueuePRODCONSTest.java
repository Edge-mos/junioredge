package ru.job4j.producerconsumer.interfaces.implemented;

import org.junit.Test;
import ru.job4j.producerconsumer.interfaces.SimpleBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SimpleBlockingQueuePRODCONSTest {
    private SimpleBlock<Integer> blockingQueue = new SimpleBlockingQueue<>(5);
    private int[] outerData = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private List<Integer> destination = new ArrayList<>();
    private Thread producer = new Thread(new Producer(blockingQueue, outerData));
    private Consumer consumer = new Consumer(blockingQueue, destination);


    @Test
    public void test() throws InterruptedException {

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();




        System.out.println("Blocking size: " + blockingQueue.size());
        System.out.println("Result = " + this.destination);
    }

}

class Producer implements Runnable {
    private SimpleBlock<Integer> blockingQueue;
    private int[] data;


    public Producer(SimpleBlock<Integer> blockingQueue, int[] outerData) {
        this.blockingQueue = blockingQueue;
        this.data = outerData;
    }

    @Override
    public void run() {

        for (int val : data) {
            this.blockingQueue.offer(val);

        }
        System.out.println("Producer DONE!");
    }
}

class Consumer extends Thread {
    private SimpleBlock<Integer> blockingQueue;
    private List<Integer> destination;
    private boolean isProceed = true;

    public Consumer(SimpleBlock<Integer> blockingQueue, List<Integer> destination) {
        this.blockingQueue = blockingQueue;
        this.destination = destination;
    }

    @Override
    public void run() {

        while (this.isProceed) {
            int result = this.blockingQueue.poll();
            System.out.println(result);
            this.destination.add(result);
        }
    }

    public void shutDown(boolean res) {

    }
}