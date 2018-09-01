package ru.job4j.bomberman.models;

import ru.job4j.bomberman.interfaces.Character;
import ru.job4j.bomberman.interfaces.MainChar;
import ru.job4j.bomberman.interfaces.PlayBoard;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Board implements PlayBoard{
    private final int rows;
    private final int cols;
    private final ReentrantLock[][] board;
    private static MainChar hero;
    private final Character[] villains;

    public Board(int rows, int cols, int villains, Cell heroInitial) {
        this.rows = rows;
        this.cols = cols;
        this.board = new ReentrantLock[rows][cols];
        hero = this.heroInitial(heroInitial);
        this.initiate();
        this.villains = new Character[villains];
    }

    @Override
    public boolean move(Cell source, Cell dist) {
        boolean result = false;
        ReentrantLock distCell = this.board[dist.getX()][dist.getY()];
        try {
            if (!distCell.isLocked() && distCell.tryLock(500, TimeUnit.MILLISECONDS)) {
                hero = hero.move(dist);
                result = true;
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.board[source.getX()][source.getY()].unlock();
        }
        return result;
    }

    @Override
    public boolean heroMove(Cell dist) {
        return this.isInRange(dist) && this.move(hero.getCurrentPos(), dist);
    }

    @Override
    public MainChar heroStatus() {
        return hero;
    }

    @Override
    public ReentrantLock get(Cell coord) {
        return this.board[coord.getX()][coord.getY()];
    }

    private void initiate() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                board[i][j] = new ReentrantLock();
            }
        }

        this.board[hero.getCurrentPos().getX()][hero.getCurrentPos().getY()].lock();
        this.board[2][2].lock();

    }

    public MainChar heroInitial(Cell cell) {
        return new Hero(cell);
    }

    private boolean isInRange(Cell dist) {
        return dist.getX() < this.rows && dist.getY() < this.cols && dist.getX() >= 0 && dist.getY() >= 0;
    }

    // TODO: 8/29/18 сделать hero static? синглтон?
}
