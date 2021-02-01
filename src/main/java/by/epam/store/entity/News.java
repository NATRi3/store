package by.epam.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class News {
private long idNews;
private String title;
private String info;
private long id_collection;
private Calendar date;
}
