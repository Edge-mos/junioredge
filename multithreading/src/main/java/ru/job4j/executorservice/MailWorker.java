package ru.job4j.executorservice;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.ToDoubleFunction;

/**MailWorker<T extends User>
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 30.07.2018.
 */
public class MailWorker<T extends User> {
    private final List<T> notificationList;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final EmailNotification emailNotification = new EmailNotification();

    public MailWorker(List<T> notificationList) {
        this.notificationList = notificationList;
    }

    private class Sender implements Runnable {
        private final T obj;

        public Sender(T obj) {
            this.obj = obj;
        }

        @Override
        public void run() {
            emailNotification.emailTo(this.obj);
        }
    }

    public void init() {
        for (T t : this.notificationList) {
            this.threadPool.submit(new Sender(t));
        }
        this.threadPool.shutdown();
        while (!threadPool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
