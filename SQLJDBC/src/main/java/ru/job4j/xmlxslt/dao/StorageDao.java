package ru.job4j.xmlxslt.dao;

import ru.job4j.xmlxslt.exeptions.DaoExeption;
import ru.job4j.xmlxslt.models.Entry;

import java.util.List;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 29.11.2018.
 */
public interface StorageDao {
    void init() throws DaoExeption;
    void drop() throws DaoExeption;
    void insertValue(int toValue) throws DaoExeption;
    List<Entry> loadValues() throws DaoExeption;
}
