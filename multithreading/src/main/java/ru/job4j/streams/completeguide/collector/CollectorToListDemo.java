package ru.job4j.streams.completeguide.collector;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CollectorToListDemo {
    // Отфильтровать список чисел по чётности
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> evenNumbers = numbers.stream()
                .filter(integer -> integer % 2 ==0)
                .collect(Collectors.toList());

        System.out.println(evenNumbers);
    }
}
