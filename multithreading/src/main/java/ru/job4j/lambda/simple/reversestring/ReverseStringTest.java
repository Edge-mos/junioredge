package ru.job4j.lambda.simple.reversestring;

public class ReverseStringTest {
    public static void main(String[] args) {

        // обратить строку в ообратном положении.
        String inWord = "Лямбдa обращается на";
        StringFunc reverse = str -> {
            StringBuilder sb = new StringBuilder();

            for (int i = str.length() - 1; i >= 0 ; i--) {
                sb.append(str.charAt(i));
            }
            return sb.toString();
        };

        System.out.println("Обращённая строка: " + reverse.func(inWord));

        // убрать пробелы из строки
        StringFunc nonSpaces = str -> {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) != ' ') {
                    sb.append(str.charAt(i));
                }
            }
            return sb.toString();
        };

        System.out.println(nonSpaces.func(inWord));
    }
}
