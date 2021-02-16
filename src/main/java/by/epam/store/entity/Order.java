package by.epam.store.entity;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class Order {
    private long id;
    private long idUser;
    private String phone;
    private String address;
    private BigDecimal price;
    private Map<Long,Integer> product;

    public Order() {
    }

    public Order(long id, long idUser, String phone, String address, BigDecimal price, Map<Long, Integer> product) {
        this.id = id;
        this.idUser = idUser;
        this.phone = phone;
        this.address = address;
        this.price = price;
        this.product = product;
    }

    public Order(long userId, String phone, String address, BigDecimal price, Map<Long, Integer> product) {
        this.idUser = userId;
        this.phone = phone;
        this.address = address;
        this.price = price;
        this.product = product;
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

    public Map<Long, Integer> getProduct() {
        return product;
    }

    public void setProduct(Map<Long, Integer> product) {
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
