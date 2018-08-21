package ru.job4j.streams.completeguide.flatmap;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Stream;

public class FlatMapArray {
    // Показать разницу в работе потоков с массивами. Обязательно надо приводить к типу(flatMapToInt). Доступна агрегация
    public static void main(String[] args) {
        int[][] arr = {{1, 2}, {3, 4}, {5, 6}};

        int[] simpArray = Arrays.stream(arr)
                .flatMapToInt(ints -> Arrays.stream(ints))
                .toArray();
        System.out.println(Arrays.toString(simpArray));

        int[][] arrMax = {{1, 2}, {347, 4}, {5, 6}};
        int maxVal = Arrays.stream(arrMax)
                .flatMapToInt(ints -> Arrays.stream(ints))
                .max().getAsInt();

        System.out.println("Max value in array: " + maxVal);
    }
}



