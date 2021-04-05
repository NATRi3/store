package by.epam.store.model.entity;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Cart.
 */
public class Cart{
    private Map<Product, Integer> products;
    private int totalAmount;
    private BigDecimal totalPrice;

    /**
     * Instantiates a new empty Cart.
     */
    public Cart() {
        products = new HashMap<>();
        totalPrice = BigDecimal.ZERO;
        totalAmount = 0;
    }

    /**
     * Add amount product to Map cart.
     *
     * @param product the product
     * @param amount  the amount of product
     */
    public void replaceProduct(Product product, int amount) {
        Integer oldProductAmount = products.put(product, amount);
        oldProductAmount = oldProductAmount == null ? 0 : oldProductAmount;
        BigDecimal sumProduct = product.getPrice().multiply(BigDecimal.valueOf(amount - oldProductAmount));
        totalPrice = totalPrice.add(sumProduct);
        recalculateTotalAmount();
    }

    /**
     * Add one product to cart. If product already exists added one more.
     *
     * @param product the product
     */
    public void replaceProduct(Product product) {
        products.merge(product,1,Integer::sum);
        totalPrice = totalPrice.add(product.getPrice());
        totalAmount++;
    }

    /**
     * Delete product from Cart.
     *
     * @param product the product
     */
    public void deleteProduct(Product product) {
        Integer productAmount = products.remove(product);
        if (productAmount != null) {
            totalPrice = totalPrice.subtract(product.getPrice().multiply(BigDecimal.valueOf(productAmount)));
            totalAmount -= productAmount;
        }
        recalculateTotalAmount();
    }

    /**
     * Delete product from Cart by id Product.
     *
     * @param idProduct the id product
     */
    public void deleteProduct(long idProduct) {
        Product product = null;
        for (Product p : products.keySet()) {
            if (p.getId() == idProduct) {
                product = p;
            }
        }
        if (product != null) {
            totalPrice = totalPrice.subtract(product.getPrice().multiply(BigDecimal.valueOf(products.get(product))));
            products.remove(product);
        }
        recalculateTotalAmount();
    }

    /**
     * Replace amount product from Cart.
     *
     * @param product the product
     * @param amount  the amount
     */
    public void replaceAmountProduct(Product product, int amount) {
        if (products.get(product) <= amount) {
            totalAmount -= products.get(product);
            products.remove(product);
        } else {
            products.replace(product, products.get(product) - amount);
        }
        recalculateTotalAmount();
    }

    /**
     * Clear the Cart.
     */
    public void clear() {
        totalAmount = 0;
        totalPrice = BigDecimal.ZERO;
        products.clear();
    }

    /**
     * Gets products.
     *
     * @return the products
     */
    public Map<Product, Integer> getProducts() {
        return products;
    }

    /**
     * Sets products.
     *
     * @param products the products
     */
    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    /**
     * Gets total amount.
     *
     * @return the total amount
     */
    public int getTotalAmount() {
        return totalAmount;
    }

    /**
     * Total amount int.
     *
     * @return the int
     */
    public int totalAmount() {
        return totalAmount;
    }

    /**
     * Sets total amount.
     *
     * @param totalAmount the total amount
     */
    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Gets total price.
     *
     * @return the total price
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets total price.
     *
     * @param totalPrice the total price
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    private void recalculateTotalAmount() {
        totalAmount = 0;
        for (Map.Entry<Product,Integer> entry : products.entrySet()) {
            if(entry.getValue()==null || entry.getValue()==0){
                products.remove(entry.getKey());
            }else {
                totalAmount += entry.getValue();
            }
        }
    }
}
