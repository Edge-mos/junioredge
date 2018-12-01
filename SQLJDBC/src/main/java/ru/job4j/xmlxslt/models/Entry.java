package ru.job4j.xmlxslt.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "entry")
public class Entry {
    private int field;

    public Entry() {
    }

    public Entry(int field) {
        this.field = field;
    }

    public int getField() {
        return this.field;
    }

    @XmlElement
    public void setField(int field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return String.valueOf(this.field);
    }
}
