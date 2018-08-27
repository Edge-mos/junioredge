package ru.job4j.threads.practice.producerconsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class ProdCons {
    private BlockingQueue<Integer> resource;
    private int [] source;
    private List<Integer> result;

    public ProdCons(int capacity, int... source) {
        this.source = source;
        this.resource = new ArrayBlockingQueue<>(capacity);
        this.result = new ArrayList<>();
    }

    public void producer() {

        for (int i : source) {
            try {
                this.resource.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void consumer() {
        try {

            while (this.resource.size() != 0) {
                int taken = this.resource.take();
                System.out.println("Taken: " + taken);
                this.result.add(taken);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public BlockingQueue<Integer> getResource() {
        return this.resource;
    }

    public int size() {
        return this.resource.size();
    }

    public List<Integer> getResult() {
        return this.result;
    }

    public static void main(String[] args) {
        int[] source ={1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 30, 50, 60, 70, 80, 90};
        ProdCons pc = new ProdCons(25, source);

        Thread prodThread = new Thread(pc::producer);

        Thread consThread = new Thread(() -> {
            try {
                Thread.sleep(10);
                pc.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        try {

            prodThread.start();
            prodThread.join();
            consThread.start();
            consThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //System.out.println("Size of resource: " + pc.size());
        System.out.println("collection :" + pc.getResource());
        System.out.println("Consumer out: " + pc.getResult());

    }

}
