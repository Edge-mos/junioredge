package ru.job4j.lambda.reference.doublereference;
@FunctionalInterface
public interface MyFunc<T> {
    boolean func(T val1, T val2);
}
