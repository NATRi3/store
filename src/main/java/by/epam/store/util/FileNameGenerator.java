package by.epam.store.util;

import java.util.UUID;

public class FileNameGenerator {
    public static String generate(String fileName) {
        StringBuilder result = new StringBuilder();
        result.append(UUID.randomUUID());
        String typeFile = fileName.substring(fileName.lastIndexOf("."));
        result.append(typeFile);
        return result.toString();
    }
}
