package ru.job4j.streams.listsream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Demo2 {
    public static void main(String[] args) {
        ////////////////////////////////
        Stream<String> phones = Stream.of("iPhone 8", "HTC U12", "Huawei Nexus 6P",
                "Samsung Galaxy S9", "LG G6", "Xiaomi MI6", "ASUS Zenfone 2",
                "Sony Xperia Z5", "Meizu Pro 6", "Lenovo S850");


        ArrayList<String> filteredPhones = phones.filter(n -> n.length() < 12).collect(
                ArrayList::new, (list, item) -> {if (item.contains("Meizu")) {
                    list.add(item);
                }},
                (list1, list2) -> list1.addAll(list2)
        );
        filteredPhones.forEach(System.out::println);
    }
}
