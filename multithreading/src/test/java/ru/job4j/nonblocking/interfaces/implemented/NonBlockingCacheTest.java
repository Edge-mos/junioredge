package ru.job4j.nonblocking.interfaces.implemented;

import org.junit.Test;
import ru.job4j.executorservice.User;
import ru.job4j.nonblocking.exeptions.OptimisticExeption;
import ru.job4j.nonblocking.interfaces.Cache;
import ru.job4j.nonblocking.models.Base;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.theInstance;
import static org.junit.Assert.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class NonBlockingCacheTest {
    private Cache<Base> nonBlockingCache = new NonBlockingCache<>();

    @Test
    public void whenAddNonInListValueThanTrue() {
      boolean result = this.nonBlockingCache.add(new Base(1, 1));
      assertThat(result, is(true));
    }

    @Test
    public void whenAddPresentValueInListThanFalse() {
        this.nonBlockingCache.add(new Base(1, 1));
        boolean result = this.nonBlockingCache.add(new Base(1, 1));
        assertThat(result, is(false));
    }

    @Test
    public void whenDeletePresentValueInListThanGetDeletedValue() {
        this.nonBlockingCache.add(new Base(1, 1));
        Base deleted = this.nonBlockingCache.delete(new Base(1, 1));
        assertThat(deleted, is(new Base(1, 1)));
    }

    @Test
    public void whenTryUpdateModelNotPresentedInListThanFalse() {
        this.nonBlockingCache.add(new Base(1, 1));
        boolean res = this.nonBlockingCache.update(new Base(3, 2));
        assertThat(res, is(false));
    }

    @Test
    public void whenUpdateModelWithNewerVersionThanTrue() {
        this.nonBlockingCache.add(new Base(1, 1));
        boolean res = this.nonBlockingCache.update(new Base(1, 2));
        assertThat(res, is(true));
    }

    @Test
            (expected = OptimisticExeption.class)
    public void whenUpdateModelWithOlderOrPresentVersionThenOptimisticExeption() {
        this.nonBlockingCache.add(new Base(1, 1));
        boolean res = this.nonBlockingCache.update(new Base(1, 1));
    }

}