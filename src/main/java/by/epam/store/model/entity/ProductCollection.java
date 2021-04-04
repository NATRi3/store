package by.epam.store.model.entity;

import java.util.Date;

/**
 * The type Product collection.
 */
public class ProductCollection {
    private long idCollection;
    private String name;
    private String info;
    private Date date;

    /**
     * Instantiates a new Product collection.
     */
    public ProductCollection() {
    }

    /**
     * Instantiates a new Product collection.
     *
     * @param idCollection the id collection
     * @param name         the name
     * @param info         the info
     * @param date         the date
     */
    private ProductCollection(long idCollection, String name, String info, Date date) {
        this.idCollection = idCollection;
        this.name = name;
        this.info = info;
        this.date = date;
    }

    /**
     * Builder product collection builder.
     *
     * @return the product collection builder
     */
    public static ProductCollectionBuilder builder() {
        return new ProductCollectionBuilder();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductCollection that = (ProductCollection) o;

        if (idCollection != that.idCollection) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (info != null ? !info.equals(that.info) : that.info != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (idCollection ^ (idCollection >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    /**
     * The type Product collection builder.
     */
    public static class ProductCollectionBuilder {
        private long idCollection;
        private String name;
        private String info;
        private Date date;

        /**
         * Instantiates a new Product collection builder.
         */
        ProductCollectionBuilder() {
        }

        /**
         * Id collection product collection builder.
         *
         * @param idCollection the id collection
         * @return the product collection builder
         */
        public ProductCollectionBuilder idCollection(long idCollection) {
            this.idCollection = idCollection;
            return this;
        }

        /**
         * Name product collection builder.
         *
         * @param name the name
         * @return the product collection builder
         */
        public ProductCollectionBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Info product collection builder.
         *
         * @param info the info
         * @return the product collection builder
         */
        public ProductCollectionBuilder info(String info) {
            this.info = info;
            return this;
        }

        /**
         * Date product collection builder.
         *
         * @param date the date
         * @return the product collection builder
         */
        public ProductCollectionBuilder date(Date date) {
            this.date = date;
            return this;
        }

        /**
         * Build product collection.
         *
         * @return the product collection
         */
        public ProductCollection build() {
            return new ProductCollection(idCollection, name, info, date);
        }

        public String toString() {
            return "ProductCollection.ProductCollectionBuilder(idCollection=" + this.idCollection + ", name=" + this.name + ", info=" + this.info + ", date=" + this.date + ")";
        }
    }
}
