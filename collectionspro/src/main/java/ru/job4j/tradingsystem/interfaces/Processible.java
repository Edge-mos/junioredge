package ru.job4j.tradingsystem.interfaces;

public interface Processible {
    void setAppInSys(Iapp application);
    boolean removeAppFromSys(Iapp application);
    void addTradingQuotes(String security);
    void showSecurityInfo(String security);
}
