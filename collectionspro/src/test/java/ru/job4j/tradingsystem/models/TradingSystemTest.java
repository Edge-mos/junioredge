package ru.job4j.tradingsystem.models;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.tradingsystem.interfaces.Processible;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class TradingSystemTest {
    private Processible ts;

    @Before
    public void setup() {
        ts = new TradingSystem();
        ts.addTradingQuotes("gazp");
        ts.addTradingQuotes("lkoh");
        ts.addTradingQuotes("sber");
        ts.addTradingQuotes("rih8");
    }
    @Test
    public void whenAddSecurityApplycation() {
        ts.setAppInSys(new Application("sber", "bid", 214., 1000));
        ts.setAppInSys(new Application("sber", "ask", 221, 500));
        ts.showSecurityInfo("sber");
    }

    @Test
    public void whenRemoveAppThanTrueIfPresentIfNotThanFalse() {
        ts.setAppInSys(new Application("sber", "bid", 214., 1000));
        ts.setAppInSys(new Application("sber", "ask", 221, 500));
        assertThat(ts.removeAppFromSys(112, "sber", "ask"), is(true));
        assertThat(ts.removeAppFromSys(111, "sber", "bid"), is(true));
        assertThat(ts.removeAppFromSys(5555, "sber", "ask"), is(false));
    }

}