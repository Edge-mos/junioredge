package ru.job4j.tradingsystem.models;

import ru.job4j.tradingsystem.interfaces.Iapp;
import ru.job4j.tradingsystem.interfaces.Icell;

import java.util.SortedMap;
import java.util.TreeMap;
/**
 * Модель строки стакана для торгов.
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 21.06.2018.
 */
public class DomCell implements Icell {
    private double price;
    private int summ;
    private String action;
    /**
     * Хранит список заявок по id в строке стакана.
     */
    private SortedMap<Integer, Iapp> apps = new TreeMap<>();

    public DomCell(double price, String action) {
        this.price = price;
        this.summ = 0;
        this.action = action;
    }

    /**
     * Добавляет заявку в строку стакана.
     * @param app заявка.
     */
    @Override
    public void addApp(Iapp app) {
        this.apps.put(app.getId(), app);
        this.summ += app.getVolume();
    }

    /**
     * Удаляет заявку из строки в стакане.
     * @param app заявка.
     * @return false или true.
     */
    @Override
    public boolean deleteApp(Iapp app) {
        if (this.apps.containsKey(app.getId())) {
            this.summCorrection(app.getVolume());
            this.apps.remove(app.getId());
            return true;
        }
        return false;
    }

    /**
     *  Достаёт первую заявку из строки стакана.
     * @return
     */
    @Override
    public Iapp getOrderedApp() {
        return this.apps.get(this.apps.firstKey());
    }

    /**
     * Корректирует отображение суммы заявок в строке стакана.
     * @param size
     */
    @Override
    public void summCorrection(int size) {
        this.summ -= size;
    }

    /**
     *
     * @return Возвращает цену в строке стакана.
     */
    @Override
    public double getPrice() {
        return this.price;
    }

    /**
     *
     * @return Возвращает сумму заявок в строке стакана.
     */
    @Override
    public int getSumm() {
        return this.summ;
    }

    @Override
    public String toString() {
        return
                this.action
                +
                "  "
                +
                this.price
                +
                "  "
                +
                this.summ;
    }
}
