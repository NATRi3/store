package by.epam.store.entity;

import by.epam.store.entity.type.TypeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
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
}
