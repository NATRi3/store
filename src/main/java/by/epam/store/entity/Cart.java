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
        int oldProductAmount;
        if(products.containsKey(product)){
            oldProductAmount = products.get(product);
            products.replace(product,amount);
        } else {
            oldProductAmount = 0;
            products.put(product,amount);
        }
        BigDecimal sumProduct = product.getPrice().multiply(BigDecimal.valueOf(amount-oldProductAmount));
        totalPrice = totalPrice.add(sumProduct);
        recalculateTotalAmount();
    }

    public void addProduct(Product product){
        if(products.containsKey(product)){
            products.replace(product,products.get(product)+1);
        } else {
            products.put(product,1);
        }
        totalPrice = totalPrice.add(product.getPrice());
        recalculateTotalAmount();
    }

    public void deleteProduct(Product product){
        if(products.remove(product)!=null) {
            totalPrice = totalPrice.subtract(product.getPrice().multiply(BigDecimal.valueOf(products.get(product))));
            totalAmount -= products.get(product);
        }
        recalculateTotalAmount();
    }

    public void deleteProduct(long idProduct){
        Product product = null;
        for (Product p:products.keySet()){
            if(p.getId()==idProduct){
                product = p;
            }
        }
        if(product!=null) {
            totalPrice = totalPrice.subtract(product.getPrice().multiply(BigDecimal.valueOf(products.get(product))));
            products.remove(product);
        }
        recalculateTotalAmount();
    }

    public void replaceAmountProduct(Product product, int amount){
        if(products.get(product)<=amount) {
            totalAmount-=products.get(product);
            products.remove(product);
        } else {
            products.replace(product,products.get(product)-amount);
        }
        recalculateTotalAmount();
    }

    public int getGetTotalAmount() {
        return totalAmount;
    }

    private void recalculateTotalAmount(){
        totalAmount = 0;
        for(Integer value : products.values()) {
            totalAmount += value;
        }
    }

    public void clear() {
        totalAmount = 0;
        totalPrice = BigDecimal.ZERO;
        products.clear();
    }
}
