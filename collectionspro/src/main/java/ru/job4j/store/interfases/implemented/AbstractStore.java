package ru.job4j.store.interfases.implemented;

import ru.job4j.generics.SimpleArray;
import ru.job4j.store.interfases.Store;
import ru.job4j.store.models.Base;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 07.05.2018.
 */
public class AbstractStore<T extends Base> implements Store<T> {
    private SimpleArray<T> store = new SimpleArray<>(10);

    @Override
    public void add(T model) {
        store.add(model);
    }

    @Override
    public boolean replace(String id, T model) {
        int index = 0;
        for (T t : this.store) {
            if (t != null && t.getId().equals(id)) {
                this.store.set(index, model);
                return true;
            }
            index++;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        int index = 0;
        for (T t : store) {
            if (t != null && t.getId().equals(id)) {
                this.store.delete(index);
                return true;
            }
            index++;
        }
        return false;
    }

    @Override
    public T findById(String id) {
        for (T t : this.store) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }
}
