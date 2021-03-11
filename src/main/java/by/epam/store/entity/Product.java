package by.epam.store.entity;


import java.math.BigDecimal;

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

        if (id != product.id) return false;
        if (idCollection != product.idCollection) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (info != null ? !info.equals(product.info) : product.info != null) return false;
        if (status != product.status) return false;
        if (price != null ? !price.equals(product.price) : product.price != null) return false;
        if (imageName != null ? !imageName.equals(product.imageName) : product.imageName != null) return false;
        return rating != null ? rating.equals(product.rating) : product.rating == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (imageName != null ? imageName.hashCode() : 0);
        result = 31 * result + (int) (idCollection ^ (idCollection >>> 32));
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }
}
