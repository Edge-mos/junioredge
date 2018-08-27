package ru.job4j.threads.practice.interrupt.interruption;
/**
 * Прерывание треда с помощью interrupr. Прерывается sleep и в блоке catch повторно вызывается Thread.currentThread().interrupt();
 */
public class InterruptionTest {
    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker("First", '*');

        Thread parallel = new Thread(worker);
        parallel.start();


        int counToShotdown = 0;

        while (true) {
            Thread.sleep(500);
            counToShotdown++;
            if (counToShotdown == 5) {
                parallel.interrupt();
                System.out.println("Interrupt!");
                break;
            }
        }

//        System.out.println(parallel.isAlive());
//        if (!parallel.isAlive()) {
//            System.out.println("Decision");
//            Thread.currentThread().interrupt();
//        }

        parallel.join();
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
        while (!Thread.currentThread().isInterrupted()) {
            this.count++;
            System.out.printf("%s : %c%c%c %d\n", this.name, this.symbol, this.symbol, this.symbol, this.count);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                //break;
            }
        }
    }


}






