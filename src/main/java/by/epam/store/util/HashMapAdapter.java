package by.epam.store.util;

import by.epam.store.entity.Product;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HashMapAdapter extends TypeAdapter<HashMap<Product, Integer>> {

    @Override
    public void write(JsonWriter writer, HashMap<Product, Integer> products) throws IOException {
        writer.beginArray();
        for (Map.Entry<Product, Integer> productIntegerEntry : products.entrySet()) {
            Product product = productIntegerEntry.getKey();
            Integer amount = productIntegerEntry.getValue();
            writer.beginObject();
            writer.name("id").value(product.getId());
            writer.name("name").value(product.getName());
            writer.name("info").value(product.getInfo());
            writer.name("imageName").value(product.getImageName());
            writer.name("price").value(product.getPrice());
            writer.name("rating").value(product.getRating());
            writer.name("idCollection").value(product.getIdCollection());
            writer.name("amount").value(amount);
            writer.endObject();
        }
        writer.endArray();
    }

    @Override
    public HashMap<Product, Integer> read(JsonReader reader) throws IOException {
        return null;
    }
}
