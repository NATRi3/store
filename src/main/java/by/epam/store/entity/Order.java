package by.epam.store.entity;

import by.epam.store.util.HashMapAdapter;
import com.google.gson.annotations.JsonAdapter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public class Order {
    private long id;
    private long idUser;
    private User user;
    private String phone;
    private String address;
    private BigDecimal price;
    private TypeStatus status;
    private Date date;
    @JsonAdapter(HashMapAdapter.class)
    private Map<Product,Integer> product;

    public Order() {
    }

    public Order(long id, long idUser, String phone, String address, BigDecimal price, Map<Product, Integer> product) {
        this.id = id;
        this.idUser = idUser;
        this.phone = phone;
        this.address = address;
        this.price = price;
        this.product = product;
    }

    public Order(long userId, String phone, String address, BigDecimal price, Map<Product, Integer> product, Date date) {
        this.idUser = userId;
        this.phone = phone;
        this.address = address;
        this.price = price;
        this.product = product;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TypeStatus getStatus() {
        return status;
    }

    public void setStatus(TypeStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDate(long date){
        this.date = new Date(date);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Map<Product, Integer> getProduct() {
        return product;
    }

    public void setProduct(Map<Product, Integer> product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (idUser != order.idUser) return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;
        if (phone != null ? !phone.equals(order.phone) : order.phone != null) return false;
        if (address != null ? !address.equals(order.address) : order.address != null) return false;
        if (price != null ? !price.equals(order.price) : order.price != null) return false;
        if (status != order.status) return false;
        if (date != null ? !date.equals(order.date) : order.date != null) return false;
        return product != null ? product.equals(order.product) : order.product == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (idUser ^ (idUser >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }
}
