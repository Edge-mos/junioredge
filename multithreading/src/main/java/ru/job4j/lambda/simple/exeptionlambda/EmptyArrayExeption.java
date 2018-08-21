package ru.job4j.lambda.simple.exeptionlambda;

public class EmptyArrayExeption extends RuntimeException{
    public EmptyArrayExeption() {
        super("Массив пуст!");
    }
}
