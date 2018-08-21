package ru.job4j.lambda.reference.objectreference;

public class MyStringOps {
    public String stringReverse(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }
}
