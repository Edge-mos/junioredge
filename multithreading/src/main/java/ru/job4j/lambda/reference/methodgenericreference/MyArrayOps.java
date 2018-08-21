package ru.job4j.lambda.reference.methodgenericreference;

public class MyArrayOps {
    static <T> int countMatching(T[] vals, T val) {
        int count = 0;

        for (T t : vals) {
            if (t == val) {
                count++;
            }
        }
        return count;
    }
}
