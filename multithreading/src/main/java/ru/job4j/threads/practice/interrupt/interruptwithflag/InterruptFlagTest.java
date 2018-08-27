package ru.job4j.threads.practice.interrupt.interruptwithflag;

/**
 * Прерывание треда с помощью флага volatile isRunning!
 */
public class InterruptFlagTest {
    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker("First", '*');
        Thread parallel = new Thread(worker);
        parallel.start();
        int counToShotdown = 0;

        while (true) {
            Thread.sleep(500);
            counToShotdown++;
            if (counToShotdown == 5) {
                worker.shutDown();
                System.out.println("ShutDown!");
                break;
            }
        }

    }
}

class Worker implements Runnable {

    private final String name;
    private final char symbol;
    private int count = 0;
    private volatile boolean isRunning = true;

    public Worker(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    @Override
    public void run() {
        this.doWork();
    }

    private void doWork() {
        while (this.isRunning) {
            this.count++;
            System.out.printf("%s : %c%c%c %d\n", this.name, this.symbol, this.symbol, this.symbol, this.count);
            this.sleep(500);
        }
    }

    public void shutDown() {
        this.isRunning = false;
    }

    private void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



