package ru.job4j.streams.completeguide.map;

import java.util.function.Function;
import java.util.stream.Stream;

public class MapDemoPhone {

    /**
     * Показать преобразование с помощью метода map(). Заходит в поток объекты Phone, а на выходе имеем поток String
     */

    static class Phone{

        private String name;
        private int price;

        public Phone(String name, int price){
            this.name=name;
            this.price=price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }

    public static void main(String[] args) {
        // получаем поток объектов
        Stream<Phone> phoneStream = Stream.of(
                new Phone("iPhone 6 S", 54000),
                new Phone("Lumia 950", 45000),
                new Phone("Samsung Galaxy S 6", 40000)
        );

        // надо вывести в консоль все названия телефонов(достать их из объектов)
        phoneStream
                .map(Phone::getName)                 // преобразовываем объект Phone в String(ссылка на метод в Phone)
                .forEach(System.out::println);       // уже идёт поток String вместо Phone и передаём методу forEach
    }
}
