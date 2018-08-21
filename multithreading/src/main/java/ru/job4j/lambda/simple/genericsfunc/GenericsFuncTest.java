package ru.job4j.lambda.simple.genericsfunc;

public class GenericsFuncTest {
    // Проверка универсального функц. интерфейса.
    public static void main(String[] args) {
        //факториал
        UniFunc<Integer> factorial = arg -> {
            int result = 1;
            for (int i = 1; i <= arg ; i++) {
                result *= i;
            }
            return result;
        };
        System.out.println(String.format("Факториал = %d", factorial.apply(5)));

        //Развернуть строку.
        String in = "Лямбдa обращается на";

        UniFunc<String> reverse = str -> {
            StringBuilder sb = new StringBuilder();
            for (int i = str.length() - 1; i >= 0; i--) {
                sb.append(str.charAt(i));
            }
            return sb.toString();
        };

        System.out.println(String.format("Исходная строка \"%s\" обращается: %s", in, reverse.apply(in)));

        //Убрать пробелы.
        UniFunc<String> nonSpaces = str -> {

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) != ' ') {
                    sb.append(str.charAt(i));
                }
            }
            return sb.toString();
        };

        System.out.println(String.format("Строка без пробелов: %s", nonSpaces.apply(in)));

        //Перевести килограммы в тонны.
        UniFunc<Integer> converter = arg -> {
            return arg / 1000;
        };
        System.out.println(String.format("Тонн: %d", converter.apply(2000)));
    }
}
