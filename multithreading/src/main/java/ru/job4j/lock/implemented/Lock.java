package ru.job4j.lock.implemented;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.lock.interfaces.SimpleLock;

/**Lock.
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 21.07.2018.
 */

@ThreadSafe
public class Lock implements SimpleLock {
    private volatile boolean isLocked = false;
    @Override
    public void lock() {
        synchronized (this) {
            while (this.isLocked) {
                try {

                    this.wait();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.isLocked = true;
        }
    }

    @Override
    public void unlock() {
        synchronized (this) {
            this.isLocked = false;
            notify();
        }
    }
}
