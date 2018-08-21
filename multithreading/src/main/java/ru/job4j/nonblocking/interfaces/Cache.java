package ru.job4j.nonblocking.interfaces;

import ru.job4j.nonblocking.models.Base;

public interface Cache<T extends Base> {
    boolean add(T model);
    boolean update(T model);
    T delete(T model);

}
