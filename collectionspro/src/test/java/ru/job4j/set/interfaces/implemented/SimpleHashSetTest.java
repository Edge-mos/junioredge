package ru.job4j.set.interfaces.implemented;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.set.interfaces.IHashSet;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.abs;
import static org.junit.Assert.*;


public class SimpleHashSetTest {
    private IHashSet<User> myHashSet;

    class User {
        private String name;
        private int children;

        public User(String name, int children) {
            this.name = name;
            this.children = children;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            User user = (User) o;

            if (children != user.children) {
                return false;
            }
            return name != null ? name.equals(user.name) : user.name == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + children;
            return abs(result);
        }

        @Override
        public String toString() {
            return "User{"
                    +
                    "name='" + name + '\''
                    +
                    ", children=" + children
                    +
                    '}';
        }
    }

    @Before
    public void setup() {
        this.myHashSet = new SimpleHashSet<>();
    }

    @Test
    public void test() {

        User user1 = new User("Test", 1);
        System.out.println(myHashSet.add(user1));

        User user2 = new User("Test2", 2);

        System.out.println(myHashSet.add(user2));

        for (Object user : myHashSet) {
            System.out.println(user);
        }




//        Set<User> set = new HashSet<>();
//        System.out.println(set.add(user1));
//        System.out.println(set.add(user2));
//        System.out.println(set.remove(user1));
//        System.out.println(set.remove(user1));
    }






































}