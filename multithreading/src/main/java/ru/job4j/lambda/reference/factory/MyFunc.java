package ru.job4j.lambda.reference.factory;
@FunctionalInterface
public interface MyFunc<R, T> {
    R func(T n);
}
