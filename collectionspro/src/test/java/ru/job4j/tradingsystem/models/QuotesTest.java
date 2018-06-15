package ru.job4j.tradingsystem.models;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.tradingsystem.interfaces.Iapp;
import ru.job4j.tradingsystem.interfaces.Iquotes;
import ru.job4j.tradingsystem.interfaces.Processible;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class QuotesTest {
    private Iquotes gazpQuotes;

    @Before
    public void setup() {
        gazpQuotes = new Quotes("gazp");
        gazpQuotes.setAplication(new Application("gazp", "ask", 143, 30));
        gazpQuotes.setAplication(new Application("gazp", "ask", 142, 20));
        gazpQuotes.setAplication(new Application("gazp", "ask", 141, 10));

        gazpQuotes.setAplication(new Application("gazp", "bid", 121, 10));
        gazpQuotes.setAplication(new Application("gazp", "bid", 122, 20));
        gazpQuotes.setAplication(new Application("gazp", "bid", 123, 30));
    }
    @Test
    public void whenQuotesSetsThanCanGetBestBidAndBestAsk() {
        assertThat(gazpQuotes.getBestBid().getPrice(), is(123.));
        assertThat(gazpQuotes.getBestAsk().getPrice(), is(141.));
        System.out.println(gazpQuotes);
    }

    @Test
    public void whenAddApplicationWithSamePriceTnanVolumeAdding() {
        gazpQuotes.setAplication(new Application("gazp", "bid", 123, 170));
        assertThat(gazpQuotes.getBestBid().getVolume(), is(200));
        gazpQuotes.setAplication(new Application("gazp", "ask", 141, 40));
        assertThat(gazpQuotes.getBestAsk().getVolume(), is(50));
    }

    @Test
    public void whenRemoveApplicationTnanTrueWhenRemoveNotPresentAppThanFalse() {
        Iapp tmp = new Application("gazp", "bid", 135, 250);
        //id добавляется в системе.
        tmp.setId(5555);
        gazpQuotes.setAplication(tmp);
        assertThat(gazpQuotes.getBestBid().getPrice(), is(135.));
        assertThat(gazpQuotes.removeAplication(5555, "bid"), is(true));
        assertThat(gazpQuotes.removeAplication(5555, "bid"), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenIncorrectActionOccuredTnanThrowsExeption() {
        //only bid or ask allowed!
        gazpQuotes.setAplication(new Application("gazp", "sell", 140, 10));
        gazpQuotes.removeAplication(0, "buy");
    }

    @Test
    public void whenDealOccuredThanConsolidationOfQoutes() {
        gazpQuotes.setAplication(new Application("gazp", "bid", 142, 20));
        assertThat(gazpQuotes.getBestAsk().getPrice(), is(142.));
        assertThat(gazpQuotes.getBestAsk().getVolume(), is(10));
    }

    @Test
    public void whenCrisisHasCome() {
        gazpQuotes.setAplication(new Application("gazp", "ask", 50, 10000000));
        //price limit!
        assertThat(gazpQuotes.getBestAsk().getPrice(), is(50.));
        assertThat(gazpQuotes.getBestBid(), is(nullValue()));
        System.out.println(gazpQuotes);
    }

    @Test
    public void whenPanicBuyTnanBearAreClosing() {
        gazpQuotes.setAplication(new Application("gazp", "bid", 379, 101000000));
        //price limit!
        assertThat(gazpQuotes.getBestBid().getPrice(), is(379.));
        assertThat(gazpQuotes.getBestAsk(), is(nullValue()));
        System.out.println(gazpQuotes);
    }

}