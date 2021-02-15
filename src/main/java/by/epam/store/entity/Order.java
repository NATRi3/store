package by.epam.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    private long id;
    private long idUser;
    private String phone;
    private String address;
    private BigDecimal price;
    private Map<Long,Integer> product;

    public Order(long userId, String phone, String address, BigDecimal price, Map<Long, Integer> product) {
        this.idUser = userId;
        this.phone = phone;
        this.address = address;
        this.price = price;
        this.product = product;
    }
}
