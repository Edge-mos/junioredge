package ru.job4j.executorservice;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class MailWorkerTest {

    private List<User> userList = new LinkedList<>(Arrays.asList(
            new User("Anton"), new User("Petr"), new User("Vladimir"),
            new User("Sergey"), new User("Liubov"), new User("Igor"),
            new User("Aleksandr"), new User("Victor"), new User("Anna"),
            new User("Maxim"), new User("Iliya"), new User("Denis"),
            new User("Donald"), new User("Barack"), new User("George")));

    private MailWorker<User> mailWorker = new MailWorker<>(userList);

    @Test
    public void test() {
        mailWorker.init();
    }

}