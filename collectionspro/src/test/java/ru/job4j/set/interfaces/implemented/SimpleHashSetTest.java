package ru.job4j.set.interfaces.implemented;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.set.interfaces.IHashSet;
import static org.hamcrest.CoreMatchers.is;
import static java.lang.Math.abs;
import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static java.lang.Math.abs;


public class SimpleHashSetTest {
    private IHashSet<User> simpleHashSet;

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
        this.simpleHashSet = new SimpleHashSet<>();
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorTest() {
        User user1 = new User("Test", 1);
        User user2 = new User("Test2", 2);
        User user3 = new User("Test3", 3);

        simpleHashSet.add(user1);
        simpleHashSet.add(user2);
        simpleHashSet.add(user3);

        Iterator<User> it = simpleHashSet.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(new User("Test", 1)));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(new User("Test2", 2)));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(new User("Test3", 3)));
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test
    public void whenAddElementThanContainsTrue() {
        User user1 = new User("Test", 1);
        assertThat(simpleHashSet.add(user1), is(true));
        User user2 = new User("Petr", 1);
        assertThat(simpleHashSet.contains(user2), is(false));
    }

    @Test
    public void whenDeleteUserFromSetTnanContainsFalse() {
        User user1 = new User("Test", 1);
        simpleHashSet.add(user1);

        assertThat(simpleHashSet.remove(user1), is(true));
        assertThat(simpleHashSet.contains(user1), is(false));
    }






































}