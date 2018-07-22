package ru.job4j.lock.interfaces;

public interface SimpleLock {
    void lock() throws InterruptedException;
    void unlock();
}
