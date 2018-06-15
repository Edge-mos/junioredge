package ru.job4j.tradingsystem.models;

import ru.job4j.tradingsystem.interfaces.Iquotes;
import ru.job4j.tradingsystem.interfaces.Iapp;

import java.util.*;

/**
 * Упрощённая модель котировок 2 уровня(стакан)
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 13.06.2018.
 */
public class Quotes implements Iquotes {
    /**
     * Заголовок стакана.
     */
    private String heading;
    /**
     * Компаратор для сортировки заявок по цене.
     */
    private OrderComparator order = new OrderComparator();
    /**
     * Список заявок на покупку.
     */
    private List<Iapp> bids = new LinkedList<>();
    /**
     * Список заявок на продажу.
     */
    private List<Iapp> asks = new LinkedList<>();

    /**
     * Конструктор. Устанавливает тикер заглавными буквами, обязательное требование.
     * @param heading тикер.
     */
    public Quotes(String heading) {
        this.heading = heading.toUpperCase();
    }

    /**
     * Постановка заявки в систему, взависимости от направления, передаёт заявку во вспомогательный метод.
     * Определяет в какой список поставить заявку - в покупку или в продажу, передаёт во вспомогательный метод.
     * Передаёт вспомогательному методу нужнфй компаратор - либо по убыванию, либо обратный.
     * Выкидывает исключение, если сторона заявки заполнена некорректно.
     * @param application объект заявки.
     */
    @Override
    public void setAplication(Iapp application) {
        switch (application.getAction()) {
            case "bid":
                this.addApplication(application, order.reversed(), this.bids);
                break;
            case "ask":
                this.addApplication(application, order, this.asks);
                break;
                default:
                    throw new IllegalArgumentException("Incorrect application action!");
        }
    }

    /**
     * Внутренний вспомогательный метод постановки заявки в систему, делает основную работу.
     * @param application объект заявки.
     * @param comp объект компоратора для сортировки.
     * @param quotes требуемый список (в покупки или в продажи).
     */
    private void addApplication(Iapp application, Comparator<Iapp> comp, List<Iapp> quotes) {
        for (Iapp quote : quotes) {
            if (quote.getPrice() == application.getPrice()) {
                quote.setVolme(quote.getVolume() + application.getVolume());
                return;
            }
        }
        quotes.add(application);
        quotes.sort(comp);
        if (this.check()) {
            this.consolidationQuotes();
        }
    }

    /**
     * Сведение встречных заявок в стакане.
     */
    private void consolidationQuotes() {
        Iapp bestBid = this.getBestBid();
        Iapp bestAsk = this.getBestAsk();
        while (bestBid.getPrice() >= bestAsk.getPrice()) {
            if (bestBid.getVolume() > bestAsk.getVolume()) {
                bestBid.setVolme(bestBid.getVolume() - bestAsk.getVolume());
                this.removeAplication(bestAsk.getId(), bestAsk.getAction());
            } else if (bestBid.getVolume() == bestAsk.getVolume()) {
                this.removeAplication(bestBid.getId(), bestBid.getAction());
                this.removeAplication(bestAsk.getId(), bestAsk.getAction());
            } else {
                bestAsk.setVolme(bestAsk.getVolume() - bestBid.getVolume());
                this.removeAplication(bestBid.getId(), bestBid.getAction());
            }
            if (this.check()) {
                bestBid = this.getBestBid();
                bestAsk = this.getBestAsk();
            } else {
                break;
            }
        }
    }

    /**
     * Вспомогательный метод, проверяет наличие заявок в списках на покупку и продажу.
     * @return true, если заявки есть в обоих списках, false, если отсутствуют заявки хотя бы в одном списке.
     */
    private boolean check() {
        return !this.bids.isEmpty() && !this.asks.isEmpty();
    }

    /**
     * Основной метод удаления заявки из списка.
     * Передаёт вспомогательному методу remove() id и нужный список(покупка/продажа).
     * @param id номер заявки в системе.
     * @param action направление(покупка/продажа).
     * @return true, в случае нахождения и удаления, false, в случае отсутствия.
     */
    @Override
    public boolean removeAplication(int id, String action) {
        boolean result = false;
        switch (action) {
            case "bid":
                result = this.remove(id, this.bids);
                break;
            case "ask":
                result = this.remove(id, this.asks);
                break;
                default:
                    throw new IllegalArgumentException("Incorrect application action!");
        }
        return result;
    }

    /**
     * @return лучшая заявка на покупку.
     */
    @Override
    public Iapp getBestBid() {

        return !this.bids.isEmpty() ? (this.bids.get(0)) : (null);
    }

    /**
     * @return лучшая заявка на продажу.
     */
    @Override
    public Iapp getBestAsk() {
        return !this.asks.isEmpty() ? (this.asks.get(0)) : (null);
    }

    /**
     * Вспомогательный метод, выполняет основную работу по поиску и удалению заявки в нужном списке.
     * @param id номер заявки в системе.
     * @param quotes нужный список(покупка/продажа).
     * @return true, в случае нахождения и удаления, false, в случае отсутствия. Передаёт значение в основной метод.
     */
    private boolean remove(int id, List<Iapp> quotes) {
        for (Iapp quote : quotes) {
            if (quote.getId() == id) {
                return quotes.remove(quote);
            }
        }
        return false;
    }

    /**
     * @return текстовое представление стакана.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("         " + this.heading + "\n");
        sb.append("  " + "id " + "  Act" + " Price " + " Vol" + "\n");
        sb.append("------------------------\n");
        for (int i = this.asks.size() - 1; i >= 0; i--) {
            sb.append("  " + asks.get(i).getId() + "  "  + asks.get(i).getAction() + " " + asks.get(i).getPrice() + "  " + asks.get(i).getVolume() + "  " + "\n");
        }
        sb.append("------------------------\n");
        for (Iapp bid : bids) {
            sb.append("  " + bid.getId() + "  " + bid.getAction() + " " + bid.getPrice() + "  " + bid.getVolume() + "  "  + "\n");
        }
        return sb.toString();
    }

    /**
     * Внутренний класс компаратора для сортировки списков на покупку или продажу.
     */
    private class OrderComparator implements Comparator<Iapp> {
        @Override
        public int compare(Iapp o1, Iapp o2) {
            return Double.compare(o1.getPrice(), o2.getPrice());
        }
    }
}
