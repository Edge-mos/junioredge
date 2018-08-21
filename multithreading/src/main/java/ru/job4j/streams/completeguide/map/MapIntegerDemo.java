package ru.job4j.streams.completeguide.map;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MapIntegerDemo {
    /**
     * Продемонстрировать приминение вариантов функции map, таких как mapToDouble
     */
    static class Thing {
       private String name;
       private Double cost;

        public Thing(String name, Double cost) {
            this.name = name;
            this.cost = cost;
        }

        public String getName() {
            return name;
        }

        public Double getCost() {
            return cost;
        }
    }
    public static void main(String[] args) {
        // узнать среднюю цену товаров в списке

        List<Thing> things = Arrays.asList(
                new Thing("Computer", 1000.),
                new Thing("Laptop", 1500.),
                new Thing("Smartphone", 500.)
        );

       double averageCost = things.stream()        // получение потока
               .mapToDouble(Thing::getCost)        // преобразование потока в double
               .average()                          // становятся доступны функции агрегирования
               .getAsDouble();                     // выходное значение Optional

        System.out.println("Средняя: " + averageCost);

        double allCost = things.stream()
                .mapToDouble(Thing::getCost)
                .sum();

        System.out.println("Сумма: " + allCost);

    }
}
