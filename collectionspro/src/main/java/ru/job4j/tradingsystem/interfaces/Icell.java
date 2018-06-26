package ru.job4j.tradingsystem.interfaces;

public interface Icell {
    void addApp(Iapp app);
    boolean deleteApp(Iapp app);
    Iapp getOrderedApp();
    void summCorrection(int size);
    double getPrice();
    int getSumm();

}
