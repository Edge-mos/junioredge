package ru.job4j.bank;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private final double[] accounts;
    private Lock bankLock;
    private Condition sufficientFunds;

    public Bank(int n, double initialBalance) {
        this.accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
        this.bankLock = new ReentrantLock();
        this.sufficientFunds = bankLock.newCondition();
    }

    public void transfer(int from, int to, double amount) throws InterruptedException {
        this.bankLock.lock();
        try {
            while (this.accounts[from] < amount) {
                this.sufficientFunds.await();
            }
            System.out.println(Thread.currentThread());
            this.accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            this.accounts[to] += amount;
            System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
            this.sufficientFunds.signalAll();
        } finally {
            this.bankLock.unlock();
        }

    }

    public double getTotalBalance() {
        this.bankLock.lock();
        try {
            return Arrays.stream(this.accounts)
                    .sum();
        } finally {
            this.bankLock.unlock();
        }

    }

    public int size() {
        return this.accounts.length;
    }
}
