package by.epam.store.entity;


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
     * Instantiates a new Cart.
     *
     * @param products    the products
     * @param totalAmount the total amount
     * @param totalPrice  the total price
     */
    private Cart(Map<Product, Integer> products, int totalAmount, BigDecimal totalPrice) {
        this.products = products;
        this.totalAmount = totalAmount;
        this.totalPrice = totalPrice;
    }

    /**
     * Instantiates a new empty Cart.
     */
    public Cart() {
        products = new HashMap<>();
        totalPrice = BigDecimal.ZERO;
        totalAmount = 0;
    }

    /**
     * Builder cart builder.
     *
     * @return the cart builder
     */
    public static CartBuilder builder() {
        return new CartBuilder();
    }

    /**
     * Add amount product to Map cart.
     *
     * @param product the product
     * @param amount  the amount of product
     */
    public void addProduct(Product product, int amount) {
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
    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + 1);
        } else {
            products.put(product, 1);
        }
        totalPrice = totalPrice.add(product.getPrice());
        recalculateTotalAmount();
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
        for (Integer value : products.values()) {
            totalAmount += value;
        }
    }

    /**
     * The type Cart builder.
     */
    public static class CartBuilder {
        private Map<Product, Integer> products;
        private int totalAmount;
        private BigDecimal totalPrice;

        /**
         * Instantiates a new Cart builder.
         */
        CartBuilder() {
        }

        /**
         * Products cart builder.
         *
         * @param products the products
         * @return the cart builder
         */
        public CartBuilder products(Map<Product, Integer> products) {
            this.products = products;
            return this;
        }

        /**
         * Total amount cart builder.
         *
         * @param totalAmount the total amount
         * @return the cart builder
         */
        public CartBuilder totalAmount(int totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        /**
         * Total price cart builder.
         *
         * @param totalPrice the total price
         * @return the cart builder
         */
        public CartBuilder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        /**
         * Build cart.
         *
         * @return the cart
         */
        public Cart build() {
            return new Cart(products, totalAmount, totalPrice);
        }

        public String toString() {
            return "Cart.CartBuilder(products=" + this.products + ", totalAmount=" + this.totalAmount + ", totalPrice=" + this.totalPrice + ")";
        }
    }
}
