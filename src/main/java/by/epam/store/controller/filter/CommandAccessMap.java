package by.epam.store.controller.filter;

import static by.epam.store.command.TypeCommand.*;
import by.epam.store.entity.TypeRole;

import java.util.HashMap;
import java.util.Set;

/**
 * The map which contains role and available commands.
 */
public class CommandAccessMap extends HashMap<TypeRole, Set<String>> {
    private static CommandAccessMap instance;

    private CommandAccessMap() {
        put(TypeRole.ADMIN, Set.of(
                ADD_COLLECTION.name().toLowerCase(),
                CHANGE_NEWS.name().toLowerCase(),
                CHANGE_NEWS_IMAGE.name().toLowerCase(),
                CHANGE_PRODUCT.name().toLowerCase(),
                CHANGE_PRODUCT_IMAGE.name().toLowerCase(),
                LOGOUT.name().toLowerCase(),
                UPLOAD_IMAGE.name().toLowerCase(),
                ACTIVATION.name().toLowerCase(),
                DELETE_NEWS.name().toLowerCase(),
                ADD_PRODUCT.name().toLowerCase(),
                ADD_NEWS.name().toLowerCase(),
                REDIRECT_TO_SINGLE_PRODUCT.name().toLowerCase(),
                CHANGE_LOCALE.name().toLowerCase(),
                CREATE_ADMIN.name().toLowerCase()));
        put(TypeRole.CLIENT, Set.of(
                CHANGE_LOCALE.name().toLowerCase(),
                LOGOUT.name().toLowerCase(),
                ACTIVATION.name().toLowerCase(),
                UPLOAD_IMAGE.name().toLowerCase(),
                REMOVE_PRODUCT_FROM_CART.name().toLowerCase(),
                REDIRECT_TO_SINGLE_PRODUCT.name().toLowerCase(),
                ADD_AMOUNT_PRODUCT_TO_CART.name().toLowerCase(),
                CREATE_ORDER.name().toLowerCase(),
                CREATE_FEEDBACK.name().toLowerCase()));
        put(TypeRole.GUEST, Set.of(
                CHANGE_LOCALE.name().toLowerCase(),
                LOGIN.name().toLowerCase(),
                REGISTRATION.name().toLowerCase(),
                ACTIVATION.name().toLowerCase(),
                UPLOAD_IMAGE.name().toLowerCase(),
                FORGOT_PASSWORD.name().toLowerCase(),
                REDIRECT_TO_SINGLE_PRODUCT.name().toLowerCase()));
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CommandAccessMap getInstance() {
        if (instance == null) {
            instance = new CommandAccessMap();
        }
        return instance;
    }
}
