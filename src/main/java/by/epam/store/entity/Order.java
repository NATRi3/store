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
    private long id_user;
    private BigDecimal price;
    private Map<Long,Integer> product;
}
