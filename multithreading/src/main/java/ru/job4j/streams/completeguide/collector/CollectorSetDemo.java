package ru.job4j.streams.completeguide.collector;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectorSetDemo {
    // Преобразуем список имен в набор(Set) имен в верхнем регистре
    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Arya", "Sansa");
        Set<String> capitalNames = names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toSet());

        System.out.println(capitalNames);
    }
}
