package by.epam.store.controller.filter;

import by.epam.store.command.TypeCommand;
import by.epam.store.entity.TypeRole;

import java.util.HashMap;
import java.util.Set;

public class CommandAccessMap extends HashMap<TypeRole, Set<String>> {
    private static CommandAccessMap instance;

    private CommandAccessMap() {
        put(TypeRole.ADMIN, Set.of(
                TypeCommand.ADD_COLLECTION.name().toLowerCase(),
                TypeCommand.CHANGE_NEWS.name().toLowerCase(),
                TypeCommand.CHANGE_NEWS_IMAGE.name().toLowerCase(),
                TypeCommand.CHANGE_PRODUCT.name().toLowerCase(),
                TypeCommand.CHANGE_PRODUCT_IMAGE.name().toLowerCase(),
                TypeCommand.LOGOUT.name().toLowerCase(),
                TypeCommand.UPLOAD_IMAGE.name().toLowerCase(),
                TypeCommand.ACTIVATION.name().toLowerCase(),
                TypeCommand.DELETE_NEWS.name().toLowerCase(),
                TypeCommand.ADD_PRODUCT.name().toLowerCase(),
                TypeCommand.ADD_NEWS.name().toLowerCase(),
                TypeCommand.REDIRECT_TO_SINGLE_PRODUCT.name().toLowerCase(),
                TypeCommand.CHANGE_LOCALE.name().toLowerCase(),
                TypeCommand.CREATE_ADMIN.name().toLowerCase()));
        put(TypeRole.CLIENT, Set.of(
                TypeCommand.CHANGE_LOCALE.name().toLowerCase(),
                TypeCommand.LOGOUT.name().toLowerCase(),
                TypeCommand.ACTIVATION.name().toLowerCase(),
                TypeCommand.UPLOAD_IMAGE.name().toLowerCase(),
                TypeCommand.REMOVE_PRODUCT_FROM_CART.name().toLowerCase(),
                TypeCommand.REDIRECT_TO_SINGLE_PRODUCT.name().toLowerCase(),
                TypeCommand.ADD_AMOUNT_PRODUCT_TO_CART.name().toLowerCase(),
                TypeCommand.CREATE_ORDER.name().toLowerCase(),
                TypeCommand.CREATE_FEEDBACK.name().toLowerCase()));
        put(TypeRole.GUEST, Set.of(
                TypeCommand.CHANGE_LOCALE.name().toLowerCase(),
                TypeCommand.LOGIN.name().toLowerCase(),
                TypeCommand.REGISTRATION.name().toLowerCase(),
                TypeCommand.ACTIVATION.name().toLowerCase(),
                TypeCommand.UPLOAD_IMAGE.name().toLowerCase(),
                TypeCommand.FORGOT_PASSWORD.name().toLowerCase(),
                TypeCommand.REDIRECT_TO_SINGLE_PRODUCT.name().toLowerCase()));
    }

    public static CommandAccessMap getInstance() {
        if (instance == null) {
            instance = new CommandAccessMap();
        }
        return instance;
    }
}
