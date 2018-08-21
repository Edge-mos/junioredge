package ru.job4j.lambda.reference.constructorref;

public class MyClassRef {
    private int val;

    public MyClassRef(int val) {
        this.val = val;
    }

    public MyClassRef() {
        this.val = 0;
    }

    public int getVal() {
        return val;
    }
}
