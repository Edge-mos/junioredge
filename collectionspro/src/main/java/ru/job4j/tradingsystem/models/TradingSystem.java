package ru.job4j.tradingsystem.models;

import ru.job4j.tradingsystem.interfaces.*;

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
    private Map<String, Idom> doms = new HashMap<>();
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
        Idom search = this.doms.get(application.getBook());
        if (search != null) {
            application.setId(TradingSystem.appId++);
            search.setAplication(application);
        }
    }

    /**
     * Удаление заявки из системы(стакана)
     * @param application заявка.
     * @return true, в случае обнаружения и снятия заявки, false в случае отсутствия.
     */
    @Override
    public boolean removeAppFromSys(Iapp application) {
        Idom search = this.doms.get(application.getBook());
        return search != null && search.removeAplication(application);
    }

    /**
     * Добавление объекта с котировками 2 уровня в систему(стакан)
     * @param security Тикер инструмента(краткое названиев системе)
     */
    @Override
    public void addTradingQuotes(String security) {
        Idom tmp = new Dom(security);
        this.doms.put(security.toUpperCase(), tmp);
    }

    /**
     * Вывод текстового представления стакана в консоль.
     * @param security тикер.
     */
    @Override
    public void showSecurityInfo(String security) {
        Idom search = this.doms.get(security.toUpperCase());
        if (search != null) {
            System.out.println(search);
        } else {
            System.out.println("Not Found!");
        }
    }
}
