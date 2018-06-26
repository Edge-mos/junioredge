package ru.job4j.tradingsystem.models;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.tradingsystem.interfaces.Iapp;
import ru.job4j.tradingsystem.interfaces.Idom;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class DomTest {

private Idom gazpQuotes;

    @Before
    public void setup() {
        gazpQuotes = new Dom("gazp");
        gazpQuotes.setAplication(new Application(111, "gazp", "ask", 140, 12));
        gazpQuotes.setAplication(new Application(112, "gazp", "ask", 131, 50));
        gazpQuotes.setAplication(new Application(113, "gazp", "ask", 129, 100));
        gazpQuotes.setAplication(new Application(114, "gazp", "ask", 130, 100));

        gazpQuotes.setAplication(new Application(115, "gazp", "bid", 121, 100));
        gazpQuotes.setAplication(new Application(116, "gazp", "bid", 119, 100));
        gazpQuotes.setAplication(new Application(117, "gazp", "bid", 125, 70));
        gazpQuotes.setAplication(new Application(118, "gazp", "bid", 127, 170));

    }
    @Test
    public void whenQuotesSetsThanCanGetBestBidAndBestAsk() {
        assertThat(gazpQuotes.getBestBid().getPrice(), is(127.));
        assertThat(gazpQuotes.getBestAsk().getPrice(), is(129.));
        System.out.println(gazpQuotes);
    }

    @Test
    public void whenAddApplicationWithSamePriceTnanVolumeAdding() {
        gazpQuotes.setAplication(new Application(119, "gazp", "bid", 127, 30));
        assertThat(gazpQuotes.getBestBidCell().getSumm(), is(200));
        gazpQuotes.setAplication(new Application(120, "gazp", "ask", 129, 40));
        assertThat(gazpQuotes.getBestAskCell().getSumm(), is(140));
        System.out.println(gazpQuotes);
    }

    @Test
    public void whenRemoveApplicationTnanTrueWhenRemoveNotPresentAppThanFalse() {
        Iapp tmp = new Application("gazp", "bid", 128, 250);
        //id добавляется в системе.
        tmp.setId(5555);
        Iapp tmp2 = new Application("gazp", "bid", 128, 250);
        //id добавляется в системе.
        tmp.setId(5556);

        gazpQuotes.setAplication(tmp);
        assertThat(gazpQuotes.getBestBidCell().getPrice(), is(128.));
        assertThat(gazpQuotes.removeAplication(tmp), is(true));
        assertThat(gazpQuotes.removeAplication(tmp2), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenIncorrectActionOccuredTnanThrowsExeption() {
        //only bid or ask allowed!
        gazpQuotes.setAplication(new Application(119, "gazp", "sell", 140, 10));
        gazpQuotes.removeAplication(new Application(115, "gazp", "buy", 121, 100));
    }

    @Test
    public void whenDealOccuredThanConsolidationOfQoutes() {
        gazpQuotes.setAplication(new Application(119, "gazp", "bid", 129, 20));
        assertThat(gazpQuotes.getBestBidCell().getPrice(), is(127.));
        assertThat(gazpQuotes.getBestAskCell().getSumm(), is(80));
    }

    @Test
    public void whenCrisisHasCome() {
        gazpQuotes.setAplication(new Application(119, "gazp", "ask", 50, 10000000));
        //price limit!
        assertThat(gazpQuotes.getBestAskCell().getPrice(), is(50.));
        assertThat(gazpQuotes.getBestBidCell(), is(nullValue()));
        System.out.println(gazpQuotes);
    }

    @Test
    public void whenPanicBuyTnanBearAreClosing() {
        gazpQuotes.setAplication(new Application(119, "gazp", "bid", 379, 101000000));
        //price limit!
        assertThat(gazpQuotes.getBestBidCell().getPrice(), is(379.));
        assertThat(gazpQuotes.getBestAskCell(), is(nullValue()));
        System.out.println(gazpQuotes);
    }

}