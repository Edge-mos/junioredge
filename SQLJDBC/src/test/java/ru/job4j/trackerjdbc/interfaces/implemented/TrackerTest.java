package ru.job4j.trackerjdbc.interfaces.implemented;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.trackerjdbc.interfaces.Process;
import ru.job4j.trackerjdbc.model.Item;
import ru.job4j.trackerjdbc.interfaces.Connect;
import ru.job4j.trackerjdbc.interfaces.InitConfig;

import java.sql.Date;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class TrackerTest {
    private Tracker tracker;

    @Before
    public void setUp() {
        this.tracker = new Tracker();
    }

    @After
    public void dropTable() {
        this.tracker.dropTable();
    }

    @Test
    public void whenAddItemToDBThanTrue() {
        Item item = new Item(1, "Пополнение", "Транзакция", Date.valueOf("2018-10-01"),
                "исполнить сегодня");
        assertThat(tracker.add(item), is(true));
        Item search = tracker.findById(1);
        assertThat(item, is(search));
    }

    @Test
    public void whenReplaceItemInDB() {
        Item item = new Item(1, "Пополнение", "Транзакция", Date.valueOf("2018-10-01"),
                "исполнить сегодня");
        Item replace = new Item(1,"Расход", "Транзакция", Date.valueOf("2018-10-02"), "в течении дня");
        tracker.add(item);
        tracker.replace(1, replace);
        assertThat(tracker.findById(1), is(replace));
    }

    @Test
    public void whenAddItemInDBThanFindSameItem() {
        Item item = new Item(1, "Пополнение", "Транзакция", Date.valueOf("2018-10-01"),
                "исполнить сегодня");
        tracker.add(item);
        assertThat(tracker.findById(1), is(item));
    }

    @Test
    public void whenDeleteItemFromDBThanTrue() {
        Item item = new Item(1, "Пополнение", "Транзакция", Date.valueOf("2018-10-01"),
                "исполнить сегодня");
        tracker.add(item);
        assertThat(tracker.delete(1), is(true));
    }

    @Test
    public void whenDeleteNonPresentedItemThanFalse() {
        assertThat(tracker.delete(1), is(false));
    }

    @Test
    public void whenGetAllItemsFromDB() {
        List<Item> init = new LinkedList<>(Arrays.asList(
                new Item(1, "Пополнение", "Транзакция", Date.valueOf("2018-10-01"),
                        "исполнить сегодня"),
                new Item(2,"Расход", "Транзакция", Date.valueOf("2018-10-02"), "в течении дня"),
                new Item(3, "Проверка", "Учёт", Date.valueOf("2018-10-03"), "в конце дня")
        ));

        for (Item item : init) {
            this.tracker.add(item);
        }

        List<Item> result = this.tracker.getAll();
        assertThat(init, is(result));
    }

    @Test
    public void whenGetAllByNamesItemsFromDB() {
        List<Item> init = new LinkedList<>(Arrays.asList(
                new Item(1, "Пополнение", "Транзакция", Date.valueOf("2018-10-01"),
                        "исполнить сегодня"),
                new Item(2,"Расход", "Транзакция", Date.valueOf("2018-10-02"), "в течении дня"),
                new Item(3, "Проверка", "Учёт", Date.valueOf("2018-10-03"), "в конце дня")
        ));

        for (Item item : init) {
            this.tracker.add(item);
        }

        List<Item> result = this.tracker.findByName("Проверка");
        assertThat(result.get(0), is(new Item(3, "Проверка", "Учёт", Date.valueOf("2018-10-03"), "в конце дня")
        ));
    }
}