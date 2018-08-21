package ru.job4j.lambda.reference.methodgenericreference;
@FunctionalInterface
public interface MyFunc<T> {
    int func(T[] vals, T val);
}
