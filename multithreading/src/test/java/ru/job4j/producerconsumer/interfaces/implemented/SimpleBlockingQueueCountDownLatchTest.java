package ru.job4j.producerconsumer.interfaces.implemented;

import org.junit.Test;
import ru.job4j.producerconsumer.interfaces.SimpleBlock;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class SimpleBlockingQueueCountDownLatchTest {
    private SimpleBlock<Integer> blockQueue = new SimpleBlockingQueue<>(5);
    private CountDownLatch countDownLatch = new CountDownLatch(2);
    private Runnable addThread = new AddThread(countDownLatch, blockQueue);
    private Runnable pollThread = new PollThread(countDownLatch, blockQueue);


    @Test
    public void whenAddAndPollThanSizeIs0() throws InterruptedException {
        new Thread(addThread).start();
        new Thread(pollThread).start();

        this.countDownLatch.await();
        assertThat(this.blockQueue.size(), is(0));
    }


}

class AddThread implements Runnable {
    private CountDownLatch countDownLatch;
    private Random random = new Random();
    private SimpleBlock<Integer> blockQueue;

    public AddThread(CountDownLatch countDownLatch, SimpleBlock<Integer> blockQueue) {
        this.countDownLatch = countDownLatch;
        this.blockQueue = blockQueue;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {

                this.blockQueue.offer(random.nextInt(100));

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.countDownLatch.countDown();
    }
}

class PollThread implements Runnable {
    private CountDownLatch countDownLatch;
    private SimpleBlock<Integer> blockQueue;
    private boolean isRunning = true;

    public PollThread(CountDownLatch countDownLatch, SimpleBlock<Integer> blockQueue) {
        this.countDownLatch = countDownLatch;
        this.blockQueue = blockQueue;
    }

    @Override
    public void run() {
        while (this.isRunning) {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.blockQueue.poll());
            if (this.blockQueue.size() == 0) {
                this.isRunning = false;
            }
        }
        this.countDownLatch.countDown();
    }
}

