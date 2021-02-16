package by.epam.store.entity;

import by.epam.store.entity.type.TypeStatus;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private long id;
    private String name;
    private String info;
    private TypeStatus status;
    private BigDecimal price;
    private String imageName;
    private long idCollection;
    private String rating;

    public Product() {
    }

    public Product(long id, String name, String info, TypeStatus status, BigDecimal price, String imageName, long idCollection, String rating) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.status = status;
        this.price = price;
        this.imageName = imageName;
        this.idCollection = idCollection;
        this.rating = rating;
    }

    public Product(String name, String info, BigDecimal price, long idCollection) {
        this.name = name;
        this.info = info;
        this.price = price;
        this.idCollection = idCollection;
    }

    public Product(long id, String name, String info, TypeStatus status, BigDecimal price, String image, long idCollection) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.status = status;
        this.price = price;
        this.imageName = image;
        this.idCollection = idCollection;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public TypeStatus getStatus() {
        return status;
    }

    public void setStatus(TypeStatus status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public long getIdCollection() {
        return idCollection;
    }

    public void setIdCollection(long idCollection) {
        this.idCollection = idCollection;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && idCollection == product.idCollection && Objects.equals(name, product.name) && Objects.equals(info, product.info) && status == product.status && Objects.equals(price, product.price) && Objects.equals(imageName, product.imageName) && Objects.equals(rating, product.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, info, status, price, imageName, idCollection, rating);
    }
}
