package ru.job4j.threadpool;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**ThreadPool
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 21.07.2018.
 */
public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            this.threads.add(new WorkerThread());
        }
    }

    public void addWork(Runnable job) throws InterruptedException {
        this.tasks.put(job);

    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            Runnable r;
            while (!ThreadPool.this.tasks.isEmpty()) {
                try {
                    r = ThreadPool.this.tasks.take();
                    r.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void shutdown() throws InterruptedException {
        for (Thread thread : threads) {
            thread.start();
            thread.join();
        }
    }
}
