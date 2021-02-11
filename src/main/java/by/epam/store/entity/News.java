package by.epam.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class News {
    private long idNews;
    private String title;
    private String info;
    private String imageName;
    private Date date;

    public News(String title, String info, Date date){
        this.title = title;
        this.info = info;
        this.date = date;
    }
}
