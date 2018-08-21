package ru.job4j.lambda.simple.arglambda;

@FunctionalInterface
public interface Func<T> {
    T apply(T obj);
}
