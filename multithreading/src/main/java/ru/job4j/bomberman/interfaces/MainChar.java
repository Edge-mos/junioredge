package ru.job4j.bomberman.interfaces;

import ru.job4j.bomberman.models.Cell;

public interface MainChar extends Character {
    @Override
    MainChar move(Cell dist);
}
