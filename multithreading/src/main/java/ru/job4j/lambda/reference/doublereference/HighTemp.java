package ru.job4j.lambda.reference.doublereference;

public class HighTemp {
    private int hTemp;

    public HighTemp(int hTemp) {
        this.hTemp = hTemp;
    }

    /**
     * Сравнить поступившую температуру с текущей.
     * @param ht2 входящая температура.
     * @return boolean.
     */
    boolean sameTemp (HighTemp ht2) {
        return this.hTemp == ht2.hTemp;
    }

    boolean lessThanTemp(HighTemp ht2) {
        return this.hTemp < ht2.hTemp;
    }
}
