package by.epam.store.model.entity;


import java.util.Date;

/**
 * The type News.
 */
public class News {
    private long idNews;
    private String title;
    private String info;
    private String imageName;
    private Date date;

    /**
     * Instantiates a new News.
     */
    public News() {
    }

    /**
     * Instantiates a new News.
     *
     * @param idNews    the id news
     * @param title     the title
     * @param info      the info
     * @param imageName the image name
     * @param date      the date
     */
    private News(long idNews, String title, String info, String imageName, Date date) {
        this.idNews = idNews;
        this.title = title;
        this.info = info;
        this.imageName = imageName;
        this.date = date;
    }

    /**
     * Builder news builder.
     *
     * @return the news builder
     */
    public static NewsBuilder builder() {
        return new NewsBuilder();
    }

    /**
     * Gets id news.
     *
     * @return the id news
     */
    public long getIdNews() {
        return idNews;
    }

    /**
     * Sets id news.
     *
     * @param idNews the id news
     */
    public void setIdNews(long idNews) {
        this.idNews = idNews;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets info.
     *
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets info.
     *
     * @param info the info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Gets image name.
     *
     * @return the image name
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Sets image name.
     *
     * @param imageName the image name
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
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

    /**
     * The type News builder.
     */
    public static class NewsBuilder {
        private long idNews;
        private String title;
        private String info;
        private String imageName;
        private Date date;

        /**
         * Instantiates a new News builder.
         */
        NewsBuilder() {
        }

        /**
         * Id news news builder.
         *
         * @param idNews the id news
         * @return the news builder
         */
        public NewsBuilder idNews(long idNews) {
            this.idNews = idNews;
            return this;
        }

        /**
         * Title news builder.
         *
         * @param title the title
         * @return the news builder
         */
        public NewsBuilder title(String title) {
            this.title = title;
            return this;
        }

        /**
         * Info news builder.
         *
         * @param info the info
         * @return the news builder
         */
        public NewsBuilder info(String info) {
            this.info = info;
            return this;
        }

        /**
         * Image name news builder.
         *
         * @param imageName the image name
         * @return the news builder
         */
        public NewsBuilder imageName(String imageName) {
            this.imageName = imageName;
            return this;
        }

        /**
         * Date news builder.
         *
         * @param date the date
         * @return the news builder
         */
        public NewsBuilder date(Date date) {
            this.date = date;
            return this;
        }

        /**
         * Build news.
         *
         * @return the news
         */
        public News build() {
            return new News(idNews, title, info, imageName, date);
        }

        public String toString() {
            return "News.NewsBuilder(idNews=" + this.idNews + ", title=" + this.title + ", info=" + this.info + ", imageName=" + this.imageName + ", date=" + this.date + ")";
        }
    }
}
