package ru.job4j.userstorage.interfaces;

import ru.job4j.userstorage.User;

import java.util.SortedMap;

public interface Istore<T extends User> {
    boolean add(T user);
    boolean update(T user, T updated);
    boolean delete(T user);
    boolean transfer(int from, int to, int amount);
    SortedMap<Integer, T> getStore();
}
