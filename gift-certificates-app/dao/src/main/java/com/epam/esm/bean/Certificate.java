package com.epam.esm.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

public class Certificate implements Serializable {

    private static final long serialVersionUID = 9078411259250099890L;

    private int id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private Instant create_date;
    private Instant last_update_date;
    private List<Tag> tagList;

    public Certificate() {

    }

    public Certificate(int id, String name, String description, double price, int duration,
                       Instant create_date, Instant last_update_date, List<Tag> tagList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.create_date = create_date;
        this.last_update_date = last_update_date;
        this.tagList = tagList;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Instant getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Instant create_date) {
        this.create_date = create_date;
    }

    public Instant getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(Instant last_update_date) {
        this.last_update_date = last_update_date;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Certificate that = (Certificate) o;

        if (id != that.id) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (duration != that.duration) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (create_date != null ? !create_date.equals(that.create_date) : that.create_date != null) return false;
        if (last_update_date != null ? !last_update_date.equals(that.last_update_date) : that.last_update_date != null)
            return false;
        return tagList != null ? tagList.equals(that.tagList) : that.tagList == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + duration;
        result = 31 * result + (create_date != null ? create_date.hashCode() : 0);
        result = 31 * result + (last_update_date != null ? last_update_date.hashCode() : 0);
        result = 31 * result + (tagList != null ? tagList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Certificate [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
                + ", duration=" + duration + ", create_date=" + create_date + ", last_update_date=" + last_update_date +
                ", tagList=" + tagList + "]";
    }
}
