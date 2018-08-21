package ru.job4j.streams.completeguide.flatmap;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class FlatMapDemo {
    /**
     * Получить имена питомцев всех людей(добавить в коллекцию)
     */
    static class Human {
        private final String name;
        private final List<String> pets;

        public Human(String name, List<String> pets) {
            this.name = name;
            this.pets = pets;
        }

        public String getName() {
            return name;
        }

        public List<String> getPets() {
            return pets;
        }
    }

    public static void main(String[] args) {
        List<Human> humans = asList(
                new Human("Sam", asList("Buddy", "Lucy")),
                new Human("Bob", asList("Frankie", "Rosie")),
                new Human("Marta", asList("Simba", "Tilly")));

        List<String> petsList = humans.stream()                     // получаем стрим из коллекции <Human>
                .map(human -> human.getPets())                      // преобразовываем Stream<Human> в Stream<List<Pet>>
                .flatMap(strings -> strings.stream())               // "разворачиваем" Stream<List<Pet>> в Stream<Pet>
                .collect(Collectors.toList());

        System.out.println(petsList);

        System.out.println("2 Variant");

        List<String> petList2 = humans.stream()
                .flatMap(human -> human.getPets().stream())
                .collect(Collectors.toList());

        System.out.println(petList2);

    }
}
