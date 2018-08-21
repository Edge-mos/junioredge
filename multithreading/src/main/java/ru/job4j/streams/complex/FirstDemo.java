package ru.job4j.streams.complex;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FirstDemo {
    public static void main(String[] args) {
        long count = IntStream.of(-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5).filter(w -> w > 0).count();
        System.out.println(count);

        String[] cities = {"Париж", "Лондон", "Мадрид"};
        Stream<String> streamCity = Arrays.stream(cities).filter(val -> val.length() == 6);
        long result = streamCity.count();
        System.out.println(result);

        String find = Stream.of(cities).filter(val -> val.equals("Лондон")).findFirst().get();
        System.out.println(find);
    }
}
