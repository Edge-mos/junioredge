package ru.job4j.userstorage.interfaces.implemented;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.userstorage.User;
import ru.job4j.userstorage.interfaces.Istore;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Многопоточное хранилище
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 03.07.2018.
 */

@ThreadSafe
public class UserStorage<T extends User> implements Istore<T> {

    @GuardedBy("storage")
    private final SortedMap<Integer, T> storage = new TreeMap<>();

    @Override
    public boolean add(T user) {
        synchronized (storage) {
            User tmp = UserStorage.this.storage.putIfAbsent(user.getId(), user);
            return tmp == null;
        }
    }

    @Override
    public boolean update(T user, T updated) {
        synchronized (storage) {
            return UserStorage.this.storage.replace(user.getId(), user, updated);
        }
    }

    @Override
    public boolean delete(T user) {
        synchronized (storage) {
            return UserStorage.this.storage.remove(user.getId(), user);
        }
    }

    @Override
    public boolean transfer(int from, int to, int amount) {
        synchronized (storage) {
            User fromUser = UserStorage.this.storage.get(from);
            User toUser = UserStorage.this.storage.get(to);
            return fromUser != null && toUser != null
                    && UserStorage.this.canTransfer(fromUser, toUser, amount);
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

    @Override
    public String toString() {
        synchronized (storage) {
            return this.storage.toString();
        }
    }
}
