package by.epam.store.controller.filter;

import by.epam.store.command.TypeCommand;
import by.epam.store.entity.TypeRole;

import java.util.HashMap;
import java.util.Set;

public class CommandAccessMap extends HashMap<TypeRole, Set<String>> {
    private static CommandAccessMap instance;

    private CommandAccessMap() {
        put(TypeRole.ADMIN, Set.of(
                TypeCommand.ADD_COLLECTION.toString().toLowerCase(),
                TypeCommand.CHANGE_NEWS.toString().toLowerCase(),
                TypeCommand.CHANGE_NEWS_IMAGE.toString().toLowerCase(),
                TypeCommand.CHANGE_PRODUCT.toString().toLowerCase(),
                TypeCommand.CHANGE_PRODUCT_IMAGE.toString().toLowerCase(),
                TypeCommand.LOGOUT.toString().toLowerCase(),
                TypeCommand.UPLOAD_IMAGE.toString().toLowerCase(),
                TypeCommand.ACTIVATION.toString().toLowerCase(),
                TypeCommand.DELETE_NEWS.toString().toLowerCase(),
                TypeCommand.ADD_PRODUCT.toString().toLowerCase(),
                TypeCommand.ADD_NEWS.toString().toLowerCase(),
                TypeCommand.REDIRECT_TO_SINGLE_PRODUCT.toString().toLowerCase(),
                TypeCommand.CHANGE_LOCALE.toString().toLowerCase()));
        put(TypeRole.CLIENT, Set.of(
                TypeCommand.CHANGE_LOCALE.toString().toLowerCase(),
                TypeCommand.LOGOUT.toString().toLowerCase(),
                TypeCommand.ACTIVATION.toString().toLowerCase(),
                TypeCommand.UPLOAD_IMAGE.toString().toLowerCase(),
                TypeCommand.REMOVE_PRODUCT_FROM_CART.toString().toLowerCase(),
                TypeCommand.REDIRECT_TO_SINGLE_PRODUCT.toString().toLowerCase(),
                TypeCommand.ADD_AMOUNT_PRODUCT_TO_CART.toString().toLowerCase(),
                TypeCommand.CREATE_ORDER.toString().toLowerCase(),
                TypeCommand.CREATE_FEEDBACK.toString().toLowerCase()));
        put(TypeRole.GUEST, Set.of(
                TypeCommand.CHANGE_LOCALE.toString().toLowerCase(),
                TypeCommand.LOGIN.toString().toLowerCase(),
                TypeCommand.REGISTRATION.toString().toLowerCase(),
                TypeCommand.ACTIVATION.toString().toLowerCase(),
                TypeCommand.UPLOAD_IMAGE.toString().toLowerCase(),
                TypeCommand.FORGOT_PASSWORD.toString().toLowerCase(),
                TypeCommand.REDIRECT_TO_SINGLE_PRODUCT.toString().toLowerCase()));
    }

    public static CommandAccessMap getInstance() {
        if (instance == null) {
            instance = new CommandAccessMap();
        }
        return instance;
    }
}
