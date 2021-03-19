package by.epam.store.pool;

import by.epam.store.entity.Order;
import by.epam.store.entity.Product;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The type No sql connection pool.
 */
public class NoSQLConnectionPool {
    private static final Logger log = LogManager.getLogger(NoSQLConnectionPool.class);
    private static final String MONGO_URI = "mongodb+srv://root:admin@cluster0.djvev.mongodb.net/test";
    private static Lock locking = new ReentrantLock();
    private static NoSQLConnectionPool instance;
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);
    private MongoDatabase database;
    private MongoClient client;

    /**
     * Instantiates a new No sql connection pool.
     */
    public NoSQLConnectionPool() {
        try {
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
            client = new MongoClient(new MongoClientURI(MONGO_URI));
            database = client
                    .getDatabase("test")
                    .withCodecRegistry(codecRegistry);
        } catch (MongoException e){
            log.error(e);
            throw e;
        }
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static NoSQLConnectionPool getInstance() {
        if (!isInitialized.get()) {
            locking.lock();
            if (instance == null) {
                instance = new NoSQLConnectionPool();
                isInitialized.set(true);
            }
            locking.unlock();
        }
        return instance;
    }

    /**
     * Close.
     */
    public void close() {
        client.close();
    }

    /**
     * Get order collection mongo collection.
     *
     * @return the mongo collection
     */
    public MongoCollection<Order> getOrderCollection(){
        return database.getCollection("test",Order.class);
    }

}
