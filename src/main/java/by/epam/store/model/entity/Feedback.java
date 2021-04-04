package by.epam.store.model.entity;


import java.util.Date;

/**
 * The type Feedback.
 */
public class Feedback {
    private long id;
    private String feedback;
    private byte evaluation;
    private long idProduct;
    private User user;
    private Date date;

    /**
     * Instantiates a new Feedback.
     */
    public Feedback() {
    }

    /**
     * Instantiates a new Feedback.
     *
     * @param feedback   the feedback
     * @param evaluation the evaluation
     * @param idProduct  the id product
     * @param user       the user
     * @param date       the date
     */
    public Feedback(String feedback, byte evaluation, long idProduct, User user, Date date) {
        this.feedback = feedback;
        this.evaluation = evaluation;
        this.idProduct = idProduct;
        this.user = user;
        this.date = date;
    }

    /**
     * Instantiates a new Feedback.
     *
     * @param id         the id
     * @param feedback   the feedback
     * @param evaluation the evaluation
     * @param idProduct  the id product
     * @param user       the user
     * @param date       the date
     */
    public Feedback(long id, String feedback, byte evaluation, long idProduct, User user, Date date) {
        this.id = id;
        this.feedback = feedback;
        this.evaluation = evaluation;
        this.idProduct = idProduct;
        this.user = user;
        this.date = date;
    }

    /**
     * Builder feedback builder.
     *
     * @return the feedback builder
     */
    public static FeedbackBuilder builder() {
        return new FeedbackBuilder();
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
     * Gets feedback.
     *
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * Sets feedback.
     *
     * @param feedback the feedback
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * Gets evaluation.
     *
     * @return the evaluation
     */
    public byte getEvaluation() {
        return evaluation;
    }

    /**
     * Sets evaluation.
     *
     * @param evaluation the evaluation
     */
    public void setEvaluation(byte evaluation) {
        this.evaluation = evaluation;
    }

    /**
     * Gets id product.
     *
     * @return the id product
     */
    public long getIdProduct() {
        return idProduct;
    }

    /**
     * Sets id product.
     *
     * @param idProduct the id product
     */
    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feedback feedback1 = (Feedback) o;

        if (id != feedback1.id) return false;
        if (evaluation != feedback1.evaluation) return false;
        if (idProduct != feedback1.idProduct) return false;
        if (feedback != null ? !feedback.equals(feedback1.feedback) : feedback1.feedback != null) return false;
        if (user != null ? !user.equals(feedback1.user) : feedback1.user != null) return false;
        return date != null ? date.equals(feedback1.date) : feedback1.date == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (feedback != null ? feedback.hashCode() : 0);
        result = 31 * result + (int) evaluation;
        result = 31 * result + (int) (idProduct ^ (idProduct >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    /**
     * The type Feedback builder.
     */
    public static class FeedbackBuilder {
        private long id;
        private String feedback;
        private byte evaluation;
        private long idProduct;
        private User user;
        private Date date;

        /**
         * Instantiates a new Feedback builder.
         */
        FeedbackBuilder() {
        }

        /**
         * Id feedback builder.
         *
         * @param id the id
         * @return the feedback builder
         */
        public FeedbackBuilder id(long id) {
            this.id = id;
            return this;
        }

        /**
         * Feedback feedback builder.
         *
         * @param feedback the feedback
         * @return the feedback builder
         */
        public FeedbackBuilder feedback(String feedback) {
            this.feedback = feedback;
            return this;
        }

        /**
         * Evaluation feedback builder.
         *
         * @param evaluation the evaluation
         * @return the feedback builder
         */
        public FeedbackBuilder evaluation(byte evaluation) {
            this.evaluation = evaluation;
            return this;
        }

        /**
         * Id product feedback builder.
         *
         * @param idProduct the id product
         * @return the feedback builder
         */
        public FeedbackBuilder idProduct(long idProduct) {
            this.idProduct = idProduct;
            return this;
        }

        /**
         * User feedback builder.
         *
         * @param user the user
         * @return the feedback builder
         */
        public FeedbackBuilder user(User user) {
            this.user = user;
            return this;
        }

        /**
         * Date feedback builder.
         *
         * @param date the date
         * @return the feedback builder
         */
        public FeedbackBuilder date(Date date) {
            this.date = date;
            return this;
        }

        /**
         * Build feedback.
         *
         * @return the feedback
         */
        public Feedback build() {
            return new Feedback(id, feedback, evaluation, idProduct, user, date);
        }

        public String toString() {
            return "Feedback.FeedbackBuilder(id=" + this.id + ", feedback=" + this.feedback + ", evaluation=" + this.evaluation + ", idProduct=" + this.idProduct + ", user=" + this.user + ", date=" + this.date + ")";
        }
    }
}
