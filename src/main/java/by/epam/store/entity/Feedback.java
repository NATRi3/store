package by.epam.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Feedback {
    private long id;
    private String feedback;
    private byte evaluation;
    private long idProduct;
    private long idUser;
}
