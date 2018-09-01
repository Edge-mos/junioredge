package ru.job4j.bomberman.interfaces;

import ru.job4j.bomberman.models.Cell;

public interface Character {
    Character move(Cell dist);
    Cell getCurrentPos();
}
