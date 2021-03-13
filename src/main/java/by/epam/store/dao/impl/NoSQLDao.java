package by.epam.store.dao.impl;

import by.epam.store.entity.Order;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Objects;

public class NoSQLDao {
    private static final String MONGO_URI = "mongodb+srv://root:admin@cluster0.djvev.mongodb.net/test";
    private static final MongoCollection<Document> collection;
    static {
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder()
                        .register(
                                ClassModel.builder(Order.class).enableDiscriminator(true).build()
                        ).automatic(true)
                        .build()
                )
        );
        collection = new MongoClient(new MongoClientURI(MONGO_URI))
                .getDatabase("test")
                .withCodecRegistry(codecRegistry).getCollection("test");
    }

    public String getOb(){
        return Objects.requireNonNull(collection.find().first()).toString();
    }
}
