package ru.job4j.tradingsystem.models;

import ru.job4j.tradingsystem.interfaces.Iapp;
import ru.job4j.tradingsystem.interfaces.Iquotes;
import ru.job4j.tradingsystem.interfaces.Processible;

import java.util.HashMap;
import java.util.Map;

/**
 * Упрощённая торговая система с заявками.
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 13.06.2018.
 */

public class TradingSystem implements Processible {
    /**
     * Хранилище "стаканов" с котировками в системе.
     */
    private Map<String, Iquotes> tradingQuotes = new HashMap<>();
    /**
     * Статическая переменная, отвечающая за присвоение id заявкам.
     */
    private static int appId = 111;

    /**
     * Добавление заявки в систему(стакан).
     * @param application объект заявки.
     */
    @Override
    public void setAppInSys(Iapp application) {
        Iquotes search = this.tradingQuotes.get(application.getBook());
        if (search != null) {
            application.setId(TradingSystem.appId++);
            search.setAplication(application);
        }
    }

    /**
     * Удаление заявки из системы(стакана)
     * @param id номер заявки в системе.
     * @param book тикер.
     * @param action сторона сделки(покупка/продажа)
     * @return true, в случае обнаружения и снятия заявки, false в случае отсутствия.
     */
    @Override
    public boolean removeAppFromSys(int id, String book, String action) {
        Iquotes search = this.tradingQuotes.get(book.toUpperCase());
        return search != null && search.removeAplication(id, action);
    }

    /**
     * Добавление объекта с котировками 2 уровня в систему(стакан)
     * @param security Тикер инструмента(краткое названиев системе)
     */
    @Override
    public void addTradingQuotes(String security) {
        Iquotes tmp = new Quotes(security);
        this.tradingQuotes.put(security.toUpperCase(), tmp);
    }

    /**
     * Вывод текстового представления стакана в консоль.
     * @param security тикер.
     */
    @Override
    public void showSecurityInfo(String security) {
        Iquotes search = this.tradingQuotes.get(security.toUpperCase());
        if (search != null) {
            System.out.println(search);
        } else {
            System.out.println("Not Found!");
        }
    }
}
