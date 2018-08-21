package ru.job4j.nonblocking.models;

public class Base {
    private int id;
    private int version;

    public Base(int id, int version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return this.id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Base base = (Base) o;

        return version == base.version;
    }

    @Override
    public int hashCode() {
        return version;
    }

    @Override
    public String toString() {
        return String.format("Base {id: %d, version: %d}", this.id, this.version);
    }
}
