package ru.job4j.streams.completeguide.collectorgroupby;

import java.util.*;
import java.util.stream.Collectors;

public class PhoneGroupBy {

    static class Phone {
        private String name;
        private String company;
        private int price;

        public Phone(String name, String comp, int price){
            this.name=name;
            this.company=comp;
            this.price = price;
        }

        public String getName() { return name; }
        public int getPrice() { return price; }
        public String getCompany() { return company; }

        @Override
        public String toString() {
            return "Phone{" +
                    "name='" + name + '\'' +
                    ", company='" + company + '\'' +
                    ", price=" + price +
                    '}';
        }
    }

    public static void main(String[] args) {
        List<Phone> phoneList = new ArrayList<>(Arrays.asList(
                new Phone("iPhone X", "Apple", 600),
                new Phone("Pixel 2", "Google", 500),
                new Phone("iPhone 8", "Apple",450),
                new Phone("Galaxy S9", "Samsung", 440),
                new Phone("Galaxy S8", "Samsung", 340)
        ));

        // Сгруппировать по компании.
        Map<String, List<Phone>> companyGroup = phoneList.stream()
                .collect(Collectors.groupingBy(Phone::getCompany));

        for (Map.Entry<String, List<Phone>> stringListEntry : companyGroup.entrySet()) {
            System.out.println(String.format("%s:\n%s\n", stringListEntry.getKey(), stringListEntry.getValue()));
        }

        // сгруппировать по компании и списку названия моделей
        Map<String, Set<String>> companyModel = phoneList.stream()
                .collect(Collectors.groupingBy(Phone::getCompany, Collectors.mapping(Phone::getName,
                        Collectors.toSet())));
        System.out.println("Компания - модели");
        System.out.println(companyModel);

        // Сгруппировать по компании и посчитать кол-во элементов в каждой группе
        Map<String, Long> companyAmount = phoneList.stream()
                .collect(Collectors.groupingBy(Phone::getCompany, Collectors.counting()));
        System.out.println("\nКол-во в группе");
        System.out.println(companyAmount);

        // Подсчитать суммарную стоимость всех смартфонов по компаниям
        Map<String, Integer> sumValue = phoneList.stream()
                .collect(Collectors.groupingBy(Phone::getCompany, Collectors.summingInt(Phone::getPrice)));
        System.out.println("\nГруппа - цена");
        System.out.println(sumValue);
        // Вывести минимальную цену в группе

       Map<String, Optional<Phone>> minByCompany = phoneList.stream()
               .collect(Collectors.groupingBy(Phone::getCompany, Collectors.minBy(Comparator.comparing(Phone::getPrice))));

        for (Map.Entry<String, Optional<Phone>> stringOptionalEntry : minByCompany.entrySet()) {
            System.out.println("Company:\n" + stringOptionalEntry.getKey() + ":" + stringOptionalEntry.getValue().get().getPrice());
        }
    }
}
