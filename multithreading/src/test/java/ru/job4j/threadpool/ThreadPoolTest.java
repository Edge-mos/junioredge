package ru.job4j.threadpool;

import org.junit.Test;

import static org.junit.Assert.*;

public class ThreadPoolTest {

    @Test
    public void test() {
        int size = Runtime.getRuntime().availableProcessors();
        System.out.println(size);
    }

}