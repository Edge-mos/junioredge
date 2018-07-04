package ru.job4j.threadsafe.interfaces.implemented;

import org.junit.Test;
import ru.job4j.threadsafe.interfaces.SyncList;

import java.util.Iterator;

import static org.junit.Assert.*;

public class SyncronizedArrayListTest {

    @Test
    public void test() {
        int index = 0;
        SyncList<Integer> syncArrayList = new SyncronizedArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            syncArrayList.add(i);
            //System.out.println(String.format("index get: %d", syncArrayList.get(i)));
            //syncArrayList.delete(i);
        }
        System.out.println(syncArrayList);





    }

}