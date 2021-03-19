package by.epam.store.entity;


import java.math.BigDecimal;

/**
 * The type Product.
 */
public class Product {
    private long id;
    private String name;
    private String info;
    private TypeStatus status;
    private BigDecimal price;
    private String imageName;
    private long idCollection;
    private String rating;
    private int countInOrder;

    /**
     * Instantiates a new Product.
     */
    public Product() {
    }

    /**
     * Instantiates a new Product.
     *
     * @param id           the id
     * @param name         the name
     * @param info         the info
     * @param status       the status
     * @param price        the price
     * @param imageName    the image name
     * @param idCollection the id collection
     * @param rating       the rating
     * @param countInOrder the count in order
     */
    private Product(long id, String name, String info, TypeStatus status, BigDecimal price, String imageName, long idCollection, String rating, int countInOrder) {
        this.id = id;
        this.name = name;
        this.info = info;
        this.status = status;
        this.price = price;
        this.imageName = imageName;
        this.idCollection = idCollection;
        this.rating = rating;
        this.countInOrder = countInOrder;
    }

    /**
     * Builder product builder.
     *
     * @return the product builder
     */
    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    /**
     * Gets count in order.
     *
     * @return the count in order
     */
    public int getCountInOrder() {
        return countInOrder;
    }

    /**
     * Sets count in order.
     *
     * @param countInOrder the count in order
     */
    public void setCountInOrder(int countInOrder) {
        this.countInOrder = countInOrder;
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets info.
     *
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets info.
     *
     * @param info the info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public TypeStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(TypeStatus status) {
        this.status = status;
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
     * Gets image name.
     *
     * @return the image name
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Sets image name.
     *
     * @param imageName the image name
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Gets id collection.
     *
     * @return the id collection
     */
    public long getIdCollection() {
        return idCollection;
    }

    /**
     * Sets id collection.
     *
     * @param idCollection the id collection
     */
    public void setIdCollection(long idCollection) {
        this.idCollection = idCollection;
    }

    /**
     * Gets rating.
     *
     * @return the rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * Sets rating.
     *
     * @param rating the rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (idCollection != product.idCollection) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (info != null ? !info.equals(product.info) : product.info != null) return false;
        if (status != product.status) return false;
        if (price != null ? !price.equals(product.price) : product.price != null) return false;
        if (imageName != null ? !imageName.equals(product.imageName) : product.imageName != null) return false;
        return rating != null ? rating.equals(product.rating) : product.rating == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (imageName != null ? imageName.hashCode() : 0);
        result = 31 * result + (int) (idCollection ^ (idCollection >>> 32));
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }

    /**
     * The type Product builder.
     */
    public static class ProductBuilder {
        private long id;
        private String name;
        private String info;
        private TypeStatus status;
        private BigDecimal price;
        private String imageName;
        private long idCollection;
        private String rating;
        private int countInOrder;

        /**
         * Instantiates a new Product builder.
         */
        ProductBuilder() {
        }

        /**
         * Id product builder.
         *
         * @param id the id
         * @return the product builder
         */
        public ProductBuilder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Name product builder.
         *
         * @param name the name
         * @return the product builder
         */
        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Info product builder.
         *
         * @param info the info
         * @return the product builder
         */
        public ProductBuilder info(String info) {
            this.info = info;
            return this;
        }

        /**
         * Status product builder.
         *
         * @param status the status
         * @return the product builder
         */
        public ProductBuilder status(TypeStatus status) {
            this.status = status;
            return this;
        }

        /**
         * Price product builder.
         *
         * @param price the price
         * @return the product builder
         */
        public ProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        /**
         * Image name product builder.
         *
         * @param imageName the image name
         * @return the product builder
         */
        public ProductBuilder imageName(String imageName) {
            this.imageName = imageName;
            return this;
        }

        /**
         * Id collection product builder.
         *
         * @param idCollection the id collection
         * @return the product builder
         */
        public ProductBuilder idCollection(long idCollection) {
            this.idCollection = idCollection;
            return this;
        }

        /**
         * Rating product builder.
         *
         * @param rating the rating
         * @return the product builder
         */
        public ProductBuilder rating(String rating) {
            this.rating = rating;
            return this;
        }

        /**
         * Count in order product builder.
         *
         * @param countInOrder the count in order
         * @return the product builder
         */
        public ProductBuilder countInOrder(int countInOrder) {
            this.countInOrder = countInOrder;
            return this;
        }

        /**
         * Build product.
         *
         * @return the product
         */
        public Product build() {
            return new Product(id, name, info, status, price, imageName, idCollection, rating, countInOrder);
        }

        public String toString() {
            return "Product.ProductBuilder(id=" + this.id + ", name=" + this.name + ", info=" + this.info + ", status=" + this.status + ", price=" + this.price + ", imageName=" + this.imageName + ", idCollection=" + this.idCollection + ", rating=" + this.rating + ", countInOrder=" + this.countInOrder + ")";
        }
    }
}
