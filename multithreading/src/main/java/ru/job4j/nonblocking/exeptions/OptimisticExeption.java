package ru.job4j.nonblocking.exeptions;

public class OptimisticExeption extends RuntimeException{
    public OptimisticExeption(String message) {
        super(message);
    }
}
