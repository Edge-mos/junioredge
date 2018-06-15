package ru.job4j.tradingsystem.interfaces;

public interface Iapp {
    int getId();
    void setId(int id);
    String getBook();
    String getAction();
    double getPrice();
    int getVolume();
    void setVolme(int vol);
}
