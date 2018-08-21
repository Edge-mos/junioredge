package ru.job4j.streams.complex.flatmap;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlatMapDemo {
    static class User {
        private String name;
        private int age;
        private List<String> phoneNumbers;

        public User(String name, int age, List<String> phoneNumbers) {

            this.name = name;
            this.age = age;
            this.phoneNumbers = phoneNumbers;
        }

        public User() {
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public List<String> getPhoneNumbers() {
            return phoneNumbers;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", phoneNumbers=" + phoneNumbers +
                    '}';
        }
    }

    public static void main(String[] args) {

        List<User> userList = new ArrayList<>(Arrays.asList(
                new User("Peter", 20, Arrays.asList("1", "2")),
                new User("Sam", 40, Arrays.asList("3", "4", "5")),
                new User("Ryan", 60, Arrays.asList("6")),
                new User("Adam", 70, Arrays.asList("7", "8"))
        ));

        userList.stream()
                .map(User::getPhoneNumbers)
                //.flatMap(Collection::stream)
                //.filter(val -> val.equals("5"))
                .forEach(System.out::println);


        User user = userList.stream()
                .filter(user1 -> user1.getPhoneNumbers().contains("5"))
                .findAny()
                .orElse(new User());
        System.out.println(user);













    }
}
