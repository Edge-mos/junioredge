package ru.job4j.producerconsumer.interfaces.implemented;

import org.junit.Test;
import ru.job4j.producerconsumer.interfaces.SimpleBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;



public class SimpleBlockingQueuePRODCONSTest {



    @Test
    public void test() throws InterruptedException {
        SimpleBlock<Integer> blockingQueue = new SimpleBlockingQueue<>(5);
        List<Integer> outerData = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> destination = new ArrayList<>();
        Thread producer = new Thread(new Producer(blockingQueue, outerData));
        Consumer consumer = new Consumer(blockingQueue, destination);


        producer.start();
        consumer.start();


        while (true) {
            if (!producer.isAlive()) {
                if (blockingQueue.size() == 0) {
                    consumer.interrupt();
                    break;
                }
            }
        }



        System.out.println("Blocking size: " + blockingQueue.size());
        System.out.println("Consumer status: " + consumer.isAlive());
        System.out.println("Result = " + destination);
        assertThat(outerData, is(destination));
        assertThat(outerData.size(), is(destination.size()));
    }

}

class Producer implements Runnable {
    private SimpleBlock<Integer> blockingQueue;
    private List<Integer> data;


    public Producer(SimpleBlock<Integer> blockingQueue, List<Integer> outerData) {
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

        while (!isInterrupted()) {
            int result = this.blockingQueue.poll();
            System.out.println(result);
            this.destination.add(result);
        }
    }
}