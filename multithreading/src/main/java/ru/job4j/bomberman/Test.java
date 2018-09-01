package ru.job4j.bomberman;

import ru.job4j.bomberman.interfaces.PlayBoard;
import ru.job4j.bomberman.models.Board;
import ru.job4j.bomberman.models.Cell;

public class Test {
    private static PlayBoard board = new Board(3, 3, 0, new Cell(0, 0));

    public static void main(String[] args) {
        System.out.println("Hero on start: " + board.heroStatus());
        System.out.println(board.get(new Cell(0, 0)));

        System.out.println("Hero on 1:1 - " + board.heroMove(new Cell(1, 1)));
        System.out.println(board.get(new Cell(0, 0)));

        System.out.println("Hero on 0:0 - " + board.heroMove(new Cell(0, 0)));
        System.out.println(board.get(new Cell(1, 1)));

        System.out.println("Hero on 1:1 - " + board.heroMove(new Cell(1, 1)));
        System.out.println(board.get(new Cell(0, 0)));








    }
}
