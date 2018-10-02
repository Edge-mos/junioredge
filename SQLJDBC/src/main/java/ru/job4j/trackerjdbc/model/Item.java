package ru.job4j.trackerjdbc.model;

import java.sql.Date;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$.
 * @since 01.10.2018.
 */
public class Item {
    private int id;
    private String name;
    private String description;
    private Date created;
    private String comment;

    public Item(String name, String description, Date created, String comment) {
        this.name = name;
        this.description = description;
        this.created = created;
        this.comment = comment;
    }

    public Item(int id, String name, String description, Date created, String comment) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (id != item.id) return false;
        if (name != null ? !name.equals(item.name) : item.name != null) return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        if (created != null ? !created.equals(item.created) : item.created != null) return false;
        return comment != null ? comment.equals(item.comment) : item.comment == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", created=" + created +
                ", comment='" + comment + '\'' +
                '}' + "\n";
    }
}



