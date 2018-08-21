package ru.job4j.streams.completeguide.map;

import java.util.Arrays;
import java.util.List;

public class MapFilterDemo {
    /**
     * Отфильтровать поток и вывести только нужные данные.
     */

    static class Car {
        private String number;
        private int year;

        public Car(String number, int year) {
            this.number = number;
            this.year = year;
        }

        public String getNumber() {
            return number;
        }

        public int getYear() {
            return year;
        }
    }

    public static void main(String[] args) {

        List<Car> cars = Arrays.asList(
                new Car("AA1111BX", 2007),
                new Car("AK5555IT", 2010),
                new Car(null, 2012),
                new Car("", 2015),
                new Car("AI3838PP", 2017));

        // нужно вывести все не пустые номера машин. выпущенных после 2010 года

        cars.stream()                                                    // получаем из коллекции поток объектов Car
                .filter(car -> car.getYear() > 2010)                     // фильтруем поток по году
                .map(Car::getNumber)                                     // преобразуем поток Car в поток String
                .filter(number -> number != null && !number.equals(""))  // фильтруем поток по условиям
                .forEach(System.out::println);                           // вывод результата

        cars.stream()
                .filter(car -> car.getYear() > 2010)
                .filter(car -> car.getNumber() != null && !car.getNumber().equals(""))
                .forEach(car -> System.out.println(car.getNumber()));                     // тот же результат
    }
}
