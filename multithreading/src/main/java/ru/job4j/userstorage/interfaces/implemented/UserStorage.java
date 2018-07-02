package ru.job4j.userstorage.interfaces.implemented;

import ru.job4j.userstorage.User;
import ru.job4j.userstorage.interfaces.Istore;

import java.util.SortedMap;
import java.util.TreeMap;

public class UserStorage<T extends User> implements Istore<T>, Runnable {
    private SortedMap<Integer, T> storage = new TreeMap<>();

    @Override
    public synchronized boolean add(T user) {
        final boolean[] result = new boolean[1];
        Thread addThread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (storage.containsValue(user)) {
                    result[0] = false;
                } else {
                    UserStorage.this.storage.put(user.getId(), user);
                    result[0] = true;
                }
            }
        });
        addThread.start();
        return result[0];
    }

    @Override
    public boolean update(T user) {
        return false;
    }

    @Override
    public boolean delete(T user) {
        return false;
    }

    @Override
    public void transfer(T user1, T user2, int amount) {

    }

    @Override
    public void run() {

    }
}
