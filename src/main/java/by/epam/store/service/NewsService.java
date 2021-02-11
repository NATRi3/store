package by.epam.store.service;

import by.epam.store.entity.News;
import by.epam.store.exception.ServiceException;
import org.apache.commons.fileupload.FileItem;

import java.util.List;

public interface NewsService {
    public List<News> getFreshNews(String count) throws ServiceException;

    List<News> getSortNews(String typeSort, String begin) throws ServiceException;

    void deleteNews(String id) throws ServiceException;

    void createNews(String title, String info) throws ServiceException;

    void changeImage(String id, List<FileItem> fileItems) throws ServiceException;

    void changeNews(String id, String title, String info) throws ServiceException;

}
