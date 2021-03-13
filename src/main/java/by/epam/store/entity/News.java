package by.epam.store.entity;


import java.util.Date;

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

    public News(String title, String info, Date date) {
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

        if (idNews != news.idNews) return false;
        if (title != null ? !title.equals(news.title) : news.title != null) return false;
        if (info != null ? !info.equals(news.info) : news.info != null) return false;
        if (imageName != null ? !imageName.equals(news.imageName) : news.imageName != null) return false;
        return date != null ? date.equals(news.date) : news.date == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (idNews ^ (idNews >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (imageName != null ? imageName.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
