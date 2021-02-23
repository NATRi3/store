package by.epam.store.entity;

import lombok.Builder;

import java.util.Date;
import java.util.Objects;
@Builder
public class Feedback {
    private long id;
    private String feedback;
    private byte evaluation;
    private long idProduct;
    private User user;
    private Date date;

    public Feedback(String feedback, byte evaluation, long idProduct, User user,Date date) {
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
        return id == feedback1.id && evaluation == feedback1.evaluation && idProduct == feedback1.idProduct && feedback.equals(feedback1.feedback) && user.equals(feedback1.user) && date.equals(feedback1.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, feedback, evaluation, idProduct, user, date);
    }
}
