package by.epam.store.entity;


import java.util.Date;

public class Feedback {
    private long id;
    private String feedback;
    private byte evaluation;
    private long idProduct;
    private User user;
    private Date date;

    public Feedback(String feedback, byte evaluation, long idProduct, User user, Date date) {
        this.feedback = feedback;
        this.evaluation = evaluation;
        this.idProduct = idProduct;
        this.user = user;
        this.date = date;
    }

    public Feedback() {
    }

    public Feedback(long id, String feedback, byte evaluation, long idProduct, User user, Date date) {
        this.id = id;
        this.feedback = feedback;
        this.evaluation = evaluation;
        this.idProduct = idProduct;
        this.user = user;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public byte getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(byte evaluation) {
        this.evaluation = evaluation;
    }

    public long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(long idProduct) {
        this.idProduct = idProduct;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

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
}
