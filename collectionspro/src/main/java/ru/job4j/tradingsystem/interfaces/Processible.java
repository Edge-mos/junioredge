package ru.job4j.tradingsystem.interfaces;

public interface Processible {
    void setAppInSys(Iapp application);
    boolean removeAppFromSys(int id, String book, String action);
    void addTradingQuotes(String security);
    void showSecurityInfo(String security);
}
