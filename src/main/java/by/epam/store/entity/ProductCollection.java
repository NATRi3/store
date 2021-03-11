package by.epam.store.entity;

import lombok.Builder;

import java.util.Date;
import java.util.Objects;
@Builder
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

        if (idCollection != that.idCollection) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (info != null ? !info.equals(that.info) : that.info != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (idCollection ^ (idCollection >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
