package by.epam.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductCollection {
    private long idCollection;
    private String name;
    private String info;
    private Date date;
}
