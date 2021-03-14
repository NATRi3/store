package by.epam.store.dao.impl;

import by.epam.store.dao.BaseDao;
import by.epam.store.dao.OrderDao;
import by.epam.store.entity.Order;
import by.epam.store.entity.Product;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.service.TypeSort;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class NoSQLDao implements OrderDao, BaseDao<Order> {
    private static final Logger log = LogManager.getLogger(NoSQLDao.class);
    private static final String MONGO_URI = "mongodb+srv://root:admin@cluster0.djvev.mongodb.net/test";
    private static final MongoCollection<Order> collection;

    static {
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder()
                        .register(
                                ClassModel.builder(Order.class).enableDiscriminator(true).build(),
                                ClassModel.builder(Product.class).enableDiscriminator(true).build()
                        ).automatic(true)
                        .build()
                )
        );
        collection = new MongoClient(new MongoClientURI(MONGO_URI))
                .getDatabase("test")
                .withCodecRegistry(codecRegistry).getCollection("test", Order.class);
    }

    @Override
    public List<Order> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Order> findEntityById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        return false;
    }

    @Override
    public boolean update(Order order) throws DaoException {
        return false;
    }

    @Override
    public Order create(Order order) throws DaoException {
        try {
            collection.insertOne(order);
            return order;
        } catch (Exception e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findUserOrders(long id) throws DaoException {
        try {
            List<Order> result = new ArrayList<>();
            for (Order order : collection.find(Filters.eq(new Document("userid", id)))) {
                result.add(order);
            }
            return result;
        } catch (Exception e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findOrdersByStatusAndSort(int beginPagination, TypeSort typeSort, TypeStatus typeStatus) throws DaoException {
        try {
            List<Order> result = new ArrayList<>();
            for (Order order : collection.find(Filters.eq(new Document("status", typeStatus)))) {
                result.add(order);
            }
            return result;
        } catch (Exception e) {
            log.error(e);
            throw new DaoException(e);
        }
    }
}
