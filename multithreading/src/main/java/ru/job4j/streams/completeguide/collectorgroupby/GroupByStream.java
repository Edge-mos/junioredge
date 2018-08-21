package ru.job4j.streams.completeguide.collectorgroupby;

import java.util.*;
import java.util.stream.Collectors;

public class GroupByStream {
    // Сгруппируем людей по фамилии
    static class Human {
        private final String name;
        private final String surname;
        private final int friendsAmount;

        public Human(String name, String surname, int friendsAmount) {
            this.name = name;
            this.surname = surname;
            this.friendsAmount = friendsAmount;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public int getFriendsAmount() {
            return friendsAmount;
        }

        @Override
        public String toString() {
            return "[name='" + name + '\'' +
                    ", surname='" + surname + '\'' +
                    ", friendsAmount=" + friendsAmount + "]";
        }
    }

    public static void main(String[] args) {
        List<Human> humans = Arrays.asList(
                new Human("Ned", "Stark", 1),
                new Human("Robb", "Stark", 2),
                new Human("Arya", "Stark", 1),
                new Human("Aegon", "Targaryen", 6),
                new Human("Daenerys", "Targaryen", 4),
                new Human("Jaime", "Lannister", 1),
                new Human("Tyrion", "Lannister", 3));


        System.out.println("Группировка по фамилии(ключ) и добавление списка по ключу");
        Map<String, List<Human>> groupBySurname = humans.stream()
                .collect(Collectors.groupingBy(Human::getSurname));

        groupBySurname.forEach((key, val) -> System.out.println(String.format(
                "%s :\n%s count = %d\n",key, val, val.stream().count()
        )) );

        System.out.println("Персонаж у которого больше всех друзей");
        Human mostFriendly = humans.stream()
                .sorted((o1, o2) -> Integer.compare(o2.getFriendsAmount(), o1.getFriendsAmount()))
                .findFirst()
                .get();

        System.out.println(mostFriendly);

        System.out.println("\nГруппировка по фамилии(ключ) и подсчёт вхождений в группу");
        Map<String, Long> groupBySurnameAndCount = humans.stream()
                .collect(Collectors.groupingBy(Human::getSurname, Collectors.counting()));
        System.out.println(groupBySurnameAndCount);

        System.out.println("\nГруппировка по фамилии(ключ) и подсчёт количества друзей в группе");
        Map<String, Integer> friendsOfGroup = humans.stream()
                .collect(Collectors.groupingBy(Human::getSurname, Collectors.summingInt(Human::getFriendsAmount)));
        System.out.println(friendsOfGroup);

        System.out.println("\nГруппировка по фамилии(ключ) и список входящих имён в группу");
        Map<String, Set<String>> namesAndMembers = humans.stream()
                .collect(Collectors.groupingBy(Human::getSurname,
                        Collectors.mapping(Human::getName, Collectors.toSet())));

        System.out.println(namesAndMembers);

    }
}
