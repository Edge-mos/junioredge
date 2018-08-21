package ru.job4j.streams.completeguide.collectorgroupby;

import java.util.*;

public class GroupByDemo {
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

        Map<String, List<Human>> map = new HashMap<>();

        for (Human human : humans) {
            String surname = human.getSurname();

//            map.putIfAbsent(surname, new ArrayList<>(Collections.singletonList(human)));
//            map.computeIfPresent(surname, (s, humans1) -> {
//                if (!humans1.contains(human)) {
//                    humans1.add(human);
//                }
//                return humans1;
//            });


            if (!map.containsKey(surname)) {
                List<Human> list = new ArrayList<>();
                list.add(human);
                map.put(surname, list);
            } else {
                List<Human> presentList = map.get(surname);
                presentList.add(human);
            }
        }

        map.forEach((s, humans1) -> System.out.println(String.format("%s [\n%s ] count = %d",s,humans1, (long) humans1.size())));
    }
}
