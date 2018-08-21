package ru.job4j.streams.completeguide.collector;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectorMapDemo {
    // Добавить спиок из потока в Map
    static class Phone {

        private String name;
        private int price;

        public Phone(String name, int price){
            this.name=name;
            this.price=price;
        }

        public String getName() {
            return name;
        }
        public int getPrice() {
            return price;
        }
    }

    public static void main(String[] args) {
        List<Phone> phones = Arrays.asList(
                new Phone("iPhone 8", 54000),
                new Phone("Nokia 9", 45000),
                new Phone("Samsung Galaxy S9", 40000),
                new Phone("LG G6", 32000)
        );

        Map<String, Integer> modelPrice = phones.stream()
                .collect(Collectors.toMap(
                        phone -> phone.getName(), phone -> phone.getPrice())
                );

        modelPrice.forEach((s, integer) -> System.out.println(s + ":" + integer));
    }
}
