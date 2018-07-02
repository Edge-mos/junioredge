package ru.job4j.userstorage.interfaces.implemented;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.userstorage.User;
import ru.job4j.userstorage.interfaces.Istore;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Модель строки стакана для торгов.
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 01.07.2018.
 */

@ThreadSafe
public class UserStorage<T extends User> implements Istore<T> {

    @GuardedBy("storage")
    private final SortedMap<Integer, T> storage = new TreeMap<>();

    @Override
    public boolean add(T user) {
        final boolean[] result = new boolean[1];
        Thread addThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (storage) {
                    User tmp = UserStorage.this.storage.putIfAbsent(user.getId(), user);
                    result[0] = tmp == null;
                }
            }
        });
        addThread.start();
        this.threadJoin(addThread);
        return result[0];
    }

    @Override
    public boolean update(T user, T updated) {
        final boolean[] result = new boolean[1];
        Thread updateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (storage) {
                        result[0] = UserStorage.this.storage.replace(user.getId(), user, updated);
                        UserStorage.this.threadSleep();
                }
            }
        });
        updateThread.start();
        this.threadJoin(updateThread);
        return result[0];
    }

    @Override
    public boolean delete(T user) {
        final boolean[] result = new boolean[1];
        Thread deleteThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (storage) {
                    result[0] = UserStorage.this.storage.remove(user.getId(), user);
                    UserStorage.this.threadSleep();
                }
            }
        });
        deleteThread.start();
        this.threadJoin(deleteThread);
        return result[0];
    }

    @Override
    public boolean transfer(int from, int to, int amount) {
        final boolean[] result = new boolean[1];
        Thread transferThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (storage) {
                    User fromUser = UserStorage.this.storage.get(from);
                    User toUser = UserStorage.this.storage.get(to);
                   result[0] = fromUser != null && toUser != null
                           && UserStorage.this.canTransfer(fromUser, toUser, amount);
                   UserStorage.this.threadSleep();
                }
            }
        });
        transferThread.start();
        this.threadJoin(transferThread);
        return result[0];
    }

    private void threadJoin(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void threadSleep() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private <T extends User> boolean canTransfer(T from, T to, int amount) {
        if (from.getAmount() >= amount) {
            from.setAmount(from.getAmount() - amount);
            to.setAmount(to.getAmount() + amount);
            return true;
        }
        return false;
    }


    @Override
    public SortedMap<Integer, T> getStore() {
        synchronized (storage) {
            return this.storage;
        }
    }

}
