package ru.job4j.streams.listsream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(7, 18, 10, 24, 17, 5));
        System.out.println("Исходный список: " + list);

        Stream<Integer> stream = list.stream();

        //Получить минимальное значение из потока.
        Optional<Integer> minVal = stream.min(Integer::compare);
        if (minVal.isPresent()) {
            System.out.println("minVal = " + minVal.get());
        }

        //Получить максимальное значение из потока.
        stream = list.stream();
        Optional<Integer> maxVal = stream.max(Integer::compare);
        if (maxVal.isPresent()) {
            System.out.println("maxVal = " + maxVal.get());
        }

        //Отсортировать поток данных
        Stream<Integer> sortedStream = list.stream().sorted();
        System.out.println("Отсортированный поток");
        sortedStream.forEach(val -> System.out.print(val + " "));
        System.out.println(System.lineSeparator());

        //Вывести только нечётные значения.
        Stream<Integer> oddVals = list.stream();
        System.out.println("Нечётные значения");
        oddVals.filter(n -> n % 2 != 0).forEach(n -> System.out.print(n + " "));
        System.out.println(System.lineSeparator());

        //Вывести только те нечётные значения, которые больше 5.
        Stream<Integer> complex = list.stream().filter(val -> val % 2 != 0).filter(val -> val > 5);
        System.out.println("Нечётные значения, больше 5 = ");
        complex.forEach(val -> System.out.print(val + " "));
        System.out.println(System.lineSeparator());

        //Метод takeWhile() выбирает из потока элементы, пока они соответствуют условию. Если попадается элемент,
        // который не соответствует условию, то метод завершает свою работу
        Stream<Integer> numbers = Stream.of(-3, -2, -1, 0, 1, 2, 3, -4, -5);
        numbers.takeWhile(n -> n < 0).forEach(n -> System.out.println(n));

        System.out.println("Выбрать ВСЕ отрицательные элементы");
        Stream<Integer> numbers2 = Stream.of(-3, -2, -1, 0, 1, 2, 3, -4, -5);
        numbers2.sorted().takeWhile(n -> n < 0).forEach(System.out::println);

        //Метод dropWhile() выполняет обратную задачу - он пропускает элементы потока,
        // которые соответствуют условию до тех пор, пока не встретит элемент, который НЕ соответствует условию:
        System.out.println("dropWhile demo: ");
        Stream<Integer> numbers3 = Stream.of(-3, -2, -1, 0, 1, 2, 3, -4, -5);
        numbers3.sorted().dropWhile(n -> n < 0).forEach(System.out::println);

        //Метод concat объединяет 2 потока
        Stream<String> people1 = Stream.of("Tom", "Bob", "Sam", "Kate", "Kate", "Tom");
        Stream<String> people2 = Stream.of("Alice", "Kate", "Sam", "Tom");
        Stream.concat(people1, people2).distinct().forEach(s -> System.out.print(s + " "));










































    }
}
