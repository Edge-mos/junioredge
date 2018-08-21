package ru.job4j.lambda.simple.arglambda;

public class LambdaAsArgumentTest {
    static String stringOp(Func<String> func, String in) {
        return func.apply(in);
    }

    public static void main(String[] args) {
        // Перевести в верхний регистр
        String str = "lambda demo mode";

        String result = stringOp(s -> s.toUpperCase(), str);
        System.out.println(result);

        // убрать пробелы
        result = stringOp(s -> s.replace(" ", ""), str);
        System.out.println(result);


    }
}
