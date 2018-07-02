package ru.job4j.userstorage.interfaces;

import ru.job4j.userstorage.User;

public interface Istore<T extends User> {
    boolean add(T user);
    boolean update(T user);
    boolean delete(T user);
    void transfer(T user1, T user2, int amount);
}
