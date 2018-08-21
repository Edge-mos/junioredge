package ru.job4j.lambda.reference.factory;

public class MyClass<T> {
    private T val;

    public MyClass(T val) {
        this.val = val;
    }

    public T getVal() {
        return val;
    }
}
