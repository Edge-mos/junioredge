package ru.job4j.Answer.producerconsumer;

import net.jcip.annotations.GuardedBy;

import java.util.List;

class Consumer extends Thread {
    /**
     * Queue to add elements to.
     */
    private final SimpleBlockingQueue<Integer> queue;
    /**
     * List where to store new values (when "doing useful work").
     */
    @GuardedBy("this")
    private final List<Integer> destination;

    /**
     * Constructs new producer to given queue.
     *
     * @param queue queue to add elements to.
     */
    Consumer(SimpleBlockingQueue<Integer> queue, List<Integer> destination) {
        this.queue = queue;
        this.destination = destination;
    }

    /**
     * Runs consumer. Consumer's "useful work" is to multiply given
     * value by 2 and write it to the destination list.
     * Length is the same as in the Producer class.
     */
    @Override
    public void run() {
        try {
            int length = 50;
            for (int i = 0; i < length; i++) {
                synchronized (this) {
                    this.destination.add(this.queue.poll() * 2);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
