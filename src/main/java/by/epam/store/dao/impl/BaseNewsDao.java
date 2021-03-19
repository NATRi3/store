package by.epam.store.dao.impl;

import by.epam.store.entity.News;
import by.epam.store.exception.DaoException;
import by.epam.store.pool.CustomConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The type Base news dao.
 */
public class BaseNewsDao implements by.epam.store.dao.NewsDao {
    private final static Logger log = LogManager.getLogger(BaseNewsDao.class);
    private static final CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
    private static final String SQL_SELECT_AMOUNT_FRESH_NEWS =
            "SELECT id_news,title,info,date,image FROM l4tsmab3ywpoc8m0.news " +
                    "ORDER BY date DESC LIMIT ?;";
    private static final String SQL_SELECT_AMOUNT_SORT_NEWS =
            "SELECT id_news, title,info,date,image FROM l4tsmab3ywpoc8m0.news ORDER BY %s LIMIT ? OFFSET ?;";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM l4tsmab3ywpoc8m0.news WHERE id_news=?";
    private static final String SQL_CREATE = "INSERT INTO l4tsmab3ywpoc8m0.news (title, info, date) VALUES (?,?,?) ";
    private static final String SQL_UPDATE_BY_ID =
            "UPDATE l4tsmab3ywpoc8m0.news SET title = ?, info = ? WHERE id_news = ?";
    private static final String SQL_UPDATE_IMAGE_BY_ID = "UPDATE l4tsmab3ywpoc8m0.news SET image = ? WHERE id_news = ? LIMIT 1";
    private static final String SQL_SELECT_BY_ID = "SELECT id_news, title, info, date, image FROM l4tsmab3ywpoc8m0.news WHERE id_news=?";


    @Override
    public List<News> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<News> findEntityById(Long id) throws DaoException {
        Optional<News> optionalNews = Optional.empty();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                optionalNews = Optional.of(getNewsFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return optionalNews;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(News news) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BY_ID)) {
            statement.setString(1, news.getTitle());
            statement.setString(2, news.getInfo());
            statement.setLong(3, news.getIdNews());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public News create(News news) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, news.getTitle());
            statement.setString(2, news.getInfo());
            statement.setLong(3, news.getDate().getTime());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                news.setIdNews(resultSet.getLong(1));
            }
            return news;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<News> findFreshNews(int count) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_AMOUNT_FRESH_NEWS)) {
            statement.setInt(1, count);
            ResultSet resultSet = statement.executeQuery();
            List<News> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(getNewsFromResultSet(resultSet));
            }
            return result;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<News> findSortNews(String typeSort, int begin, int count) throws DaoException {
        String sql = String.format(SQL_SELECT_AMOUNT_SORT_NEWS, typeSort);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, count);
            statement.setInt(2, begin);
            ResultSet resultSet = statement.executeQuery();
            List<News> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(getNewsFromResultSet(resultSet));
            }
            return result;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean changeImage(long id, String image) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_IMAGE_BY_ID)) {
            statement.setString(1, image);
            statement.setLong(2, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    private News getNewsFromResultSet(ResultSet resultSet) throws SQLException {
        return News
                .builder()
                .idNews(resultSet.getLong(DataBaseColumn.ID_NEWS))
                .title(resultSet.getString(DataBaseColumn.NEWS_TITLE))
                .info(resultSet.getString(DataBaseColumn.NEWS_INFO))
                .date(new Date(resultSet.getLong(DataBaseColumn.DATE)))
                .imageName(resultSet.getString(DataBaseColumn.NEWS_IMAGE))
                .build();
    }
}
