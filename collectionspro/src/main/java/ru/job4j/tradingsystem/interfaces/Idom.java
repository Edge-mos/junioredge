package ru.job4j.tradingsystem.interfaces;

public interface Idom {
    void setAplication(Iapp application);
    boolean removeAplication(Iapp application);
    Iapp getBestBid();
    Iapp getBestAsk();
    Icell getBestBidCell();
    Icell getBestAskCell();
}
