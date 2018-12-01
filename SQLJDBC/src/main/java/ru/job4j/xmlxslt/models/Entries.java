package ru.job4j.xmlxslt.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "entries")
public class Entries {

    @XmlElement(name = "entry")
    private List<Entry> entryList = new LinkedList<>();

    public Entries() {
    }

    public Entries(List<Entry> entryList) {
        this.entryList = entryList;
    }

    public void add(Entry entry) {
        this.entryList.add(entry);
    }

    @Override
    public String toString() {
        return this.entryList.toString();
    }
}
