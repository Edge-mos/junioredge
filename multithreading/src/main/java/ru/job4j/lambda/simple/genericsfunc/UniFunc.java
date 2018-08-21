package ru.job4j.lambda.simple.genericsfunc;

@FunctionalInterface
public interface UniFunc<T> {
    T apply(T obj);
}
