package ru.job4j.trackerjdbc.interfaces;

import ru.job4j.trackerjdbc.model.Item;

import java.util.List;

public interface Process extends AutoCloseable {
    boolean add(Item obj);
    void replace(int id, Item obj);
    Item findById(int id);
    boolean delete(int id);
    List<Item> getAll();
    List<Item> findByName(String objName);
    void dropTable();
}
