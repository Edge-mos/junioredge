package ru.job4j.bomberman.interfaces;

import ru.job4j.bomberman.models.Cell;

import java.util.concurrent.locks.ReentrantLock;

public interface PlayBoard {
    boolean move(Cell source, Cell dist);
    boolean heroMove(Cell dist);
    MainChar heroStatus();
    ReentrantLock get(Cell coord);
}
