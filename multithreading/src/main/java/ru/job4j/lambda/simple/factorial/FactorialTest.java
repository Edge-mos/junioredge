package ru.job4j.lambda.simple.factorial;

public class FactorialTest {
    public static void main(String[] args) {

        NumericFunc factorial = n -> {
            int result = 1;

            for (int i = 1; i <= n; i++) {
                result = result * i;
            }
            return result;
        };
        System.out.println(factorial.func(5));
    }
}
