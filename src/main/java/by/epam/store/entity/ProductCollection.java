package by.epam.store.entity;

import java.util.Date;
import java.util.Objects;

public class ProductCollection {
    private long idCollection;
    private String name;
    private String info;
    private Date date;

    public ProductCollection() {
    }

    public ProductCollection(long idCollection, String name, String info, Date date) {
        this.idCollection = idCollection;
        this.name = name;
        this.info = info;
        this.date = date;
    }

    public ProductCollection(String name, String info, Date date) {
        this.name = name;
        this.info = info;
        this.date = date;
    }

    public long getIdCollection() {
        return idCollection;
    }

    public void setIdCollection(long idCollection) {
        this.idCollection = idCollection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCollection that = (ProductCollection) o;
        return idCollection == that.idCollection && Objects.equals(name, that.name) && Objects.equals(info, that.info) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCollection, name, info, date);
    }
}
