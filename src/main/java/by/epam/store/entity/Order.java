package by.epam.store.entity;

import by.epam.store.util.HashMapAdapter;
import com.google.gson.annotations.JsonAdapter;
import org.bson.codecs.pojo.annotations.BsonId;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The type Order.
 */
public class Order {
    @BsonId
    private long id;
    private long idUser;
    private User user;
    private String phone;
    private String address;
    private BigDecimal price;
    private Date date;
    @JsonAdapter(HashMapAdapter.class)
    private Map<Product, Integer> product;
    private List<Product> productList;

    /**
     * Instantiates a new Order.
     */
    public Order() {
    }

    private Order(long id, long idUser, User user, String phone, String address, BigDecimal price, Date date, Map<Product, Integer> product, List<Product> productList) {
        this.id = id;
        this.idUser = idUser;
        this.user = user;
        this.phone = phone;
        this.address = address;
        this.price = price;
        this.date = date;
        this.product = product;
        this.productList = productList;
    }


    public static OrderBuilder builder() {
        return new OrderBuilder();
    }

    /**
     * Gets product list.
     *
     * @return the product list
     */
    public List<Product> getProductList() {
        return productList;
    }

    /**
     * Sets product list.
     *
     * @param productList the product list
     */
    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Date from long.
     *
     * @param date the date
     */
    public void dateFromLong(long date) {
        this.date = new Date(date);
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets id user.
     *
     * @return the id user
     */
    public long getIdUser() {
        return idUser;
    }

    /**
     * Sets id user.
     *
     * @param idUser the id user
     */
    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets product.
     *
     * @return the product
     */
    public Map<Product, Integer> getProduct() {
        return product;
    }

    /**
     * Sets product.
     *
     * @param product the product
     */
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
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (product != null ? product.hashCode() : 0);
        return result;
    }

    public static class OrderBuilder {
        private long id;
        private long idUser;
        private User user;
        private String phone;
        private String address;
        private BigDecimal price;
        private Date date;
        private Map<Product, Integer> product;
        private List<Product> productList;

        OrderBuilder() {
        }

        public OrderBuilder id(long id) {
            this.id = id;
            return this;
        }

        public OrderBuilder idUser(long idUser) {
            this.idUser = idUser;
            return this;
        }

        public OrderBuilder user(User user) {
            this.user = user;
            return this;
        }

        public OrderBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public OrderBuilder address(String address) {
            this.address = address;
            return this;
        }

        public OrderBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public OrderBuilder date(Date date) {
            this.date = date;
            return this;
        }

        public OrderBuilder product(Map<Product, Integer> product) {
            this.product = product;
            return this;
        }

        public OrderBuilder productList(List<Product> productList) {
            this.productList = productList;
            return this;
        }

        public Order build() {
            return new Order(id, idUser, user, phone, address, price, date, product, productList);
        }

        public String toString() {
            return "Order.OrderBuilder(id=" + this.id + ", idUser=" + this.idUser + ", user=" + this.user + ", phone=" + this.phone + ", address=" + this.address + ", price=" + this.price + ", date=" + this.date + ", product=" + this.product + ", productList=" + this.productList + ")";
        }
    }
}
