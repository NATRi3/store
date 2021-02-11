package by.epam.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Data
public class Cart {
    private Map<Product,Integer> products;
    private int totalAmount;
    private BigDecimal totalPrice;

    public Cart(){
        products = new HashMap<>();
        totalPrice = BigDecimal.ZERO;
        totalAmount = 0;
    }

    public void addProduct(Product product, int amount){
        if(products.containsKey(product)){
            products.replace(product,products.get(product)+amount);
        } else {
            products.put(product,amount);
        }
        totalAmount+=amount;
        totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(amount)));
    }

    public void deleteProduct(Product product){
        if(products.remove(product)!=null) {
            totalPrice = totalPrice.subtract(product.getPrice().multiply(BigDecimal.valueOf(products.get(product))));
            totalAmount -= products.get(product);
        }
    }

    public void deleteProduct(long idProduct){
        Product product = null;
        for (Product p:products.keySet()){
            if(p.getId()==idProduct){
                product = p;
            }
        }
        if(product!=null) {
            totalAmount -= products.get(product);
            totalPrice = totalPrice.subtract(product.getPrice().multiply(BigDecimal.valueOf(products.get(product))));
            products.remove(product);
        }
    }

    public void replaceAmountProduct(Product product, int amount){
        if(products.get(product)<=amount) {
            totalAmount-=products.get(product);
            products.remove(product);
        } else {
            totalAmount-=amount;
            products.replace(product,products.get(product)-amount);
        }

    }

    public int getGetTotalAmount() {
        return totalAmount;
    }
}
