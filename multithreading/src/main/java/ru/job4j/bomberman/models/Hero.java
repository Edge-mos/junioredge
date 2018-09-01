package ru.job4j.bomberman.models;

import ru.job4j.bomberman.interfaces.Character;
import ru.job4j.bomberman.interfaces.MainChar;

public final class Hero implements MainChar{

    private final Cell coordinates;

    public Hero(Cell coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public Hero move(Cell dist) {
        return new Hero(dist);
    }

    @Override
    public Cell getCurrentPos() {
        return this.coordinates;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "x = " + this.coordinates.getX() +
                "; y = " + this.coordinates.getY() +
                '}';
    }
}
