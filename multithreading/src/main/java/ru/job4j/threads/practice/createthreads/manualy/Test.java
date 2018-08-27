package ru.job4j.threads.practice.createthreads.manualy;

public class Test {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Main thread start");


        Worker worker1 = new Worker("first", '!');
        Thread first = new Thread(worker1);
        Thread second = new Thread(new Worker("second", '*'));
        first.start();
        second.start();
        first.join();

        System.out.println("Worker1 counter: " + worker1.getCounter());




        System.out.println("Main thread finish");
    }
}

class Worker implements Runnable {

    private final String name;
    private final char symbol;
    private int counter = 0;

    public Worker(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public void run() {
        this.doWork();
    }

    private void doWork() {
        for (int i = 0; i < 100; i++) {
            counter++;
            System.out.printf("%s : %c%c%c %d\n", this.name, this.symbol, this.symbol, this.symbol, i);
        }
    }
}
