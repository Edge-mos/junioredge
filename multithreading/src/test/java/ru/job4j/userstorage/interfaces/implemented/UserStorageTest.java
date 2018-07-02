package ru.job4j.userstorage.interfaces.implemented;

import org.junit.Test;
import ru.job4j.userstorage.User;
import ru.job4j.userstorage.interfaces.Istore;

import static org.junit.Assert.*;

public class UserStorageTest {

    @Test
    public void test() {
        Istore<User> userStorage = new UserStorage<>();
        for (int i = 0; i < 5; i++) {
            userStorage.add(new User(i, i));
            userStorage.update(new User(0, 0), new User(111, 111));
            userStorage.update(new User(9, 9), new User(99, 99));
            userStorage.delete(new User(1, 1));
        }

        System.out.println(userStorage.getStore());
        System.out.println(userStorage.transfer(3, 4, 2));
        System.out.println(userStorage.getStore());


    }

}