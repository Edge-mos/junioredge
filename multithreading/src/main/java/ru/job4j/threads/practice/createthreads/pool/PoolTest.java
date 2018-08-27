package ru.job4j.threads.practice.createthreads.pool;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PoolTest {
    public static void main(String[] args) throws InterruptedException {

//        parallel.start();
        int counter = 0;

        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        Thread parallel = new Thread(new Worker("First", '*'));
        Thread parallel2 = new Thread(new Worker("Second", '!'));

        threadPool.submit(parallel);
        threadPool.submit(parallel2);
        threadPool.shutdown();

        threadPool.awaitTermination(3, TimeUnit.SECONDS);


        while (true) {
            counter++;
            Thread.sleep(500);
            if (counter == 5) {
                System.out.println("Interruption!");
                List<Runnable> runnables = threadPool.shutdownNow();
                System.out.println("Await termination: " + runnables.size());


                break;
            }
        }


    }
}

class Worker implements Runnable {

    private final String name;
    private final char symbol;
    private int count = 0;

    public Worker(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public void run() {
        this.doWork();
    }

    private void doWork() {

        try {

            while (!Thread.currentThread().isInterrupted()) {
                this.count++;
                Thread.sleep(500);
                System.out.printf("%s : %c%c%c %d\n", this.name, this.symbol, this.symbol, this.symbol, this.count);
            }

        } catch (InterruptedException e) {
            System.out.println("Stop!!!");

        }

    }
}


