package by.epam.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Collection {
    private long idCollection;
    private String name;
    private String info;
    private Calendar date;
}
