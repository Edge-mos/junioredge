package ru.job4j.tradingsystem.models;

import ru.job4j.tradingsystem.interfaces.Iapp;

/**
 * Модель заявки для торгов.
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 13.06.2018.
 */

public class Application implements Iapp {
    private int id;
    private String book;
    private String action;
    private double price;
    private int volume;

    public Application(String book, String action, double price, int volume) {
        this.book = book;
        this.action = action;
        this.price = price;
        this.volume = volume;
    }

    public Application(int id, String book, String action, double price, int volume) {
        this.id = id;
        this.book = book;
        this.action = action;
        this.price = price;
        this.volume = volume;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getBook() {
        return this.book.toUpperCase();
    }

    @Override
    public String getAction() {
        return this.action;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public int getVolume() {
        return this.volume;
    }

    @Override
    public void setVolme(int vol) {
        this.volume = vol;
    }

    @Override
    public String toString() {
        return "Application{"
                +
                "id=" + id
                +
                ", book='" + this.getBook() + '\''
                +
                ", action='" + action + '\''
                +
                ", price=" + price
                +
                ", volume=" + volume
                +
                '}';
    }
}
