package ru.job4j.tradingsystem.models;

import ru.job4j.tradingsystem.interfaces.Iapp;
import ru.job4j.tradingsystem.interfaces.Icell;
import ru.job4j.tradingsystem.interfaces.Idom;

import java.util.*;

/**
 * Модель стакана для торгов.
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 21.06.2018.
 */

public class Dom implements Idom {
    private String heading;
    private Comparator<Double> order = new OrderComparator();
    private NavigableMap<Double, Icell> bids = new TreeMap<>(order.reversed());
    private NavigableMap<Double, Icell> asks = new TreeMap<>(order);

    public Dom(String heading) {
        this.heading = heading;
    }

    @Override
    public void setAplication(Iapp application) {
        switch (application.getAction()) {
            case "bid":
                this.addAppToCell(application, this.bids);
                break;
            case "ask":
                this.addAppToCell(application, this.asks);
                break;
            default:
                throw new IllegalArgumentException("Incorrect application action!");
        }
    }

    private void addAppToCell(Iapp application, Map<Double, Icell> cell) {
        if (!cell.containsKey(application.getPrice())) {
            cell.put(application.getPrice(), new DomCell(application.getPrice(), application.getAction()));
        }
        Icell search = cell.get(application.getPrice());
        search.addApp(application);
        if (this.check()) {
            this.consolidationAppInDome();
        }
    }

    private void consolidationAppInDome() {
        Icell bestBidCell = this.getBestBidCell();
        Icell bestAskCell = this.getBestAskCell();
        Iapp bid;
        Iapp ask;
        while (bestBidCell.getPrice() >= bestAskCell.getPrice()) {
            bid = this.getBestBid();
            ask = this.getBestAsk();
            if (bid.getVolume() >= ask.getVolume()) {
                bid.setVolme(bid.getVolume() - ask.getVolume());
                bestBidCell.summCorrection(ask.getVolume());
                this.removeAplication(ask);
                this.removeCellFromDom(bid, this.bids);
            } else {
                ask.setVolme(ask.getVolume() - bid.getVolume());
                bestAskCell.summCorrection(bid.getVolume());
                this.removeAplication(bid);
                this.removeCellFromDom(ask, this.asks);
            }
            if (this.check()) {
                bestBidCell = this.getBestBidCell();
                bestAskCell = this.getBestAskCell();
            } else {
                break;
            }
        }
    }

    @Override
    public boolean removeAplication(Iapp application) {
        switch (application.getAction()) {
            case "bid":
                return this.removeAppFromCell(application, this.bids);
            case "ask":
                return this.removeAppFromCell(application, this.asks);
            default:
                throw new IllegalArgumentException("Incorrect application action!");
        }
    }

    private boolean removeAppFromCell(Iapp application, Map<Double, Icell> cell) {
        boolean result = cell.containsKey(application.getPrice()) && (cell.get(application.getPrice()).deleteApp(application));
        if (result) {
            this.removeCellFromDom(application, cell);
        }
        return result;
    }

    private void removeCellFromDom(Iapp application, Map<Double, Icell> cell) {
        if (cell.get(application.getPrice()).getSumm() == 0) {
            cell.remove(application.getPrice());
        }
    }

    @Override
    public Icell getBestBidCell() {
        return  !this.bids.isEmpty() ? (this.bids.firstEntry().getValue()) : (null);
    }

    @Override
    public Icell getBestAskCell() {
        return !this.asks.isEmpty() ? (this.asks.firstEntry().getValue()) : (null);
    }

    @Override
    public Iapp getBestBid() {
        return this.getBestBidCell().getOrderedApp();
    }

    @Override
    public Iapp getBestAsk() {
        return this.getBestAskCell().getOrderedApp();
    }
    private boolean check() {
        return this.getBestBidCell() != null && this.getBestAskCell() != null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("     ").append(this.heading).append("\n\n");
        for (Icell icell : this.asks.descendingMap().values()) {
            sb.append(icell).append(System.lineSeparator());
        }
        sb.append("------------------------\n");
        for (Icell icell : this.bids.values()) {
            sb.append(icell).append("\n");
        }
        return sb.toString();
    }

    private class OrderComparator implements Comparator<Double> {
        @Override
        public int compare(Double o1, Double o2) {
            return Double.compare(o1, o2);
        }
    }
}
