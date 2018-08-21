package ru.job4j.streams.complex.objectfilter;

import java.util.stream.Stream;

public class ObjectFilterDemo {
    public static void main(String[] args) {
        Stream<Phone> phones = Stream.of(new Phone("iPhone 6S", 54000), new Phone("Lumia 950", 45000),
                new Phone("Samsung Galaxy S6", 40000));
        // Отфильтровать по цене больше 40 000 и вывести на экран.

        phones.filter(v -> v.getPrice() > 40000).forEach(System.out::println);


        Stream<Phone> phones2 = Stream.of(new Phone("iPhone 6S", 54000), new Phone("Lumia 950", 45000),
                new Phone("Samsung Galaxy S6", 40000));

        phones2.filter(v -> v.getPrice() > 40000).map(v -> v.getName()).forEach(System.out::println);

        Stream<Phone> phones3 = Stream.of(new Phone("iPhone 6S", 54000), new Phone("Lumia 950", 45000),
                new Phone("Samsung Galaxy S6", 40000));


        // Получить плосткое отображение
        phones3.flatMap(p -> Stream.of(
                String.format("Название: %s, цена без скидки %d", p.getName(), p.getPrice()),
                String.format("Название: %s, цена со скидкой %d", p.getName(), p.getPrice() / 2)

        )).forEach(System.out::println);

    }
}
