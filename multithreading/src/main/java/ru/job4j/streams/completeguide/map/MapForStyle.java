package ru.job4j.streams.completeguide.map;

import java.util.Arrays;
import java.util.List;

public class MapForStyle {
    // Можно делать в стиле работы со значениями в цикле
    public static void main(String[] args) {
        // просто перемножить значения на 2
        List<Integer> numbers = Arrays.asList(1, 3, 5, 7);
        numbers.stream()
                .map(val -> val * 2)
                .forEach(System.out::println);


        System.out.println("As double");

        // привести тип к double и перемножить на 2
        numbers.stream()
                .mapToDouble(value -> value * 2)
                .forEach(System.out::println);

        // отфильтровать значения, например больше
        numbers.stream()
                .filter(n -> n >= 4)
                .map(n -> n * 10)
                .forEach(System.out::println);
    }
}
