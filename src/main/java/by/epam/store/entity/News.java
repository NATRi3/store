package by.epam.store.entity;



import java.util.Date;
import java.util.Objects;

public class News {
    private long idNews;
    private String title;
    private String info;
    private String imageName;
    private Date date;

    public News() {
    }

    public News(long idNews, String title, String info, String imageName, Date date) {
        this.idNews = idNews;
        this.title = title;
        this.info = info;
        this.imageName = imageName;
        this.date = date;
    }

    public News(String title, String info, Date date){
        this.title = title;
        this.info = info;
        this.date = date;
    }

    public long getIdNews() {
        return idNews;
    }

    public void setIdNews(long idNews) {
        this.idNews = idNews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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
        News news = (News) o;
        return idNews == news.idNews && Objects.equals(title, news.title) && Objects.equals(info, news.info) && Objects.equals(imageName, news.imageName) && Objects.equals(date, news.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNews, title, info, imageName, date);
    }
}
