package ru.job4j.store.models;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 07.05.2018.
 */
public abstract class Base {
    private final String id;

    protected Base(final String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
}
