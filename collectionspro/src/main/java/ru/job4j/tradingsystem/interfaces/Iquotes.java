package ru.job4j.tradingsystem.interfaces;

public interface Iquotes {
    void setAplication(Iapp application);
    boolean removeAplication(int id, String action);
    Iapp getBestBid();
    Iapp getBestAsk();
}
