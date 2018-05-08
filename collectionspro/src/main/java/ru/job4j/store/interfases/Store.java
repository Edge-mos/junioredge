package ru.job4j.store.interfases;

import ru.job4j.store.models.Base;
/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 07.05.2018.
 */
public interface Store<T extends Base> {
    void add(T model);
    boolean replace(String id, T model);
    boolean delete(String id);
    T findById(String id);
}
