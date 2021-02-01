package by.epam.store.entity;

import by.epam.store.entity.type.TypeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    private long id;
    private String name;
    private String info;
    private BigDecimal price;
    private TypeStatus status;

    public Product(String name, String info, BigDecimal price, long id_user, String imageName) {
        this.name = name;
        this.info = info;
        this.price = price;
    }
}
