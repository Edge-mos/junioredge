package ru.job4j.myhashmap.hashmap.interfaces.implemented;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.myhashmap.hashmap.interfaces.SimpleMap;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static java.lang.Math.abs;
import static org.junit.Assert.*;


public class MyMapTest {
    private SimpleMap<User, Integer> map;
    private SimpleMap<User, Integer> map2;
    private SimpleMap<User, Integer> map3;
    private User user1 = new User("Ivan", 1);
    private User user2 = new User("ivAn", 1);
    private User user3 = new User("ivan", 1);
    private User user4 = new User("Ivan", 1);
    private User user5 = new User("Ivan", 1);
    private User user6 = new User("Petr", 1);
    private User user7 = new User("Vovan", 2);

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
        map = new MyMap<>();
        map2 = new MyMap<>(0);
        map3 = new MyMap<>(1, 0.20);

    }
    @Test
    public void whenAddElementsAndCollisionsOccursThanTrue() {
        assertThat(map.insert(user1, 1), is(true));
        assertThat(map.insert(user2, 222), is(true));
        assertThat(map.insert(user3, 333), is(true));
        assertThat(map.insert(user4, 55555), is(true));
        assertThat(map.insert(user5, 99999), is(true));
        assertThat(map.insert(user6, 777), is(true));
        assertThat(map.insert(user7, 88888), is(true));
        assertThat(map.insert(null, null), is(false));
        assertThat(map.isEmpty(), is(false));
        assertThat(map.size(), is(5));
    }

    @Test
    public void whenAddSameElementsThanLastElementOverridesPrevios() {
        map.insert(user4, 444);
        map.insert(user5, 555);
        assertThat(map.size(), is(1));
        assertThat(map.get(user5), is(555));
    }

    @Test
    public void whenDeleteElementThanTrueAndSizeReduce() {
        map.insert(user6, 777);
        assertThat(map.isEmpty(), is(false));
        assertThat(map.size(), is(1));
        assertThat(map.delete(user6), is(true));
        assertThat(map.isEmpty(), is(true));
        assertThat(map.size(), is(0));
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorTest() {
        map.insert(user1, 11);
        map.insert(user2, 22);
        map.insert(user7, 77);
        map.insert(user6, 88);

        Iterator it = map.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next().hashCode(), is(user1.hashCode()));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next().hashCode(), is(user2.hashCode()));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next().hashCode(), is(user7.hashCode()));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next().hashCode(), is(user6.hashCode()));
        it.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void concurrentModificationTest() {
        map.insert(user1, 11);
        map.insert(user2, 22);
        map.insert(user7, 77);

        Iterator it = map.iterator();
        while (it.hasNext()) {
            it.next();
            map.delete(user2);
        }
    }

    @Test
    public void whenCleanMapThanSizeIsZero() {
        map.insert(user1, 11);
        map.insert(user2, 22);
        map.insert(user7, 77);
        map.clear();
        assertThat(map.isEmpty(), is(true));
        assertThat(map.size(), is(0));
    }
}