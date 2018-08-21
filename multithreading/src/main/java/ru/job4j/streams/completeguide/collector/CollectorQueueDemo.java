package ru.job4j.streams.completeguide.collector;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CollectorQueueDemo {
    // Соберем список не пустых строк в Queue
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jaime", "Daenerys", "", "Tyrion", "");
        Queue<String> presentNames = names.stream()
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(LinkedList::new));
        System.out.println(presentNames);
    }
}
