package by.epam.store.entity;

import by.epam.store.entity.type.TypeStatus;
import by.epam.store.util.HashMapAdapter;
import com.google.gson.annotations.JsonAdapter;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Objects;


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

    public Order(long userId, String phone, String address, BigDecimal price, Map<Product, Integer> product) {
        this.idUser = userId;
        this.phone = phone;
        this.address = address;
        this.price = price;
        this.product = product;
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
        return id == order.id && idUser == order.idUser && Objects.equals(phone, order.phone) && Objects.equals(address, order.address) && Objects.equals(price, order.price) && Objects.equals(product, order.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUser, phone, address, price, product);
    }
}
