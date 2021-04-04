package by.epam.store.controller.filter;

import by.epam.store.controller.command.TypeCommandAsync;
import by.epam.store.model.entity.TypeRole;

import java.util.HashMap;
import java.util.Set;

/**
 * The map which contains role and available async commands.
 */
public class AsyncCommandAccessMap extends HashMap<TypeRole, Set<String>> {
    private static AsyncCommandAccessMap INSTANCE;

    private AsyncCommandAccessMap() {
        put(TypeRole.ADMIN, Set.of(
                TypeCommandAsync.GET_LIST_ORDERS.name().toLowerCase(),
                TypeCommandAsync.CHANGE_USER_STATUS.name().toLowerCase(),
                TypeCommandAsync.DELETE_FEEDBACK.name().toLowerCase(),
                TypeCommandAsync.SEARCH_PRODUCT.name().toLowerCase(),
                TypeCommandAsync.GET_LIST_NEWS.name().toLowerCase(),
                TypeCommandAsync.BLOCK_PRODUCT.name().toLowerCase(),
                TypeCommandAsync.UNBLOCK_PRODUCT.name().toLowerCase(),
                TypeCommandAsync.ACTIVATE_PRODUCT.name().toLowerCase(),
                TypeCommandAsync.DEACTIVATE_PRODUCT.name().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_FEEDBACK.name().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_RANDOM.name().toLowerCase(),
                TypeCommandAsync.GET_LIST_COLLECTION.name().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_BY_COLLECTION.name().toLowerCase(),
                TypeCommandAsync.GET_FRESH_NEWS.name().toLowerCase(),
                TypeCommandAsync.GET_LIST_USERS_BY_ROLE_STATUS.name().toLowerCase()));
        put(TypeRole.CLIENT, Set.of(
                TypeCommandAsync.GET_USER_ORDERS.name().toLowerCase(),
                TypeCommandAsync.SEARCH_PRODUCT.name().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_RANDOM.name().toLowerCase(),
                TypeCommandAsync.GET_LIST_COLLECTION.name().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_BY_COLLECTION.name().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_FEEDBACK.name().toLowerCase(),
                TypeCommandAsync.GET_FRESH_NEWS.name().toLowerCase(),
                TypeCommandAsync.ADD_PRODUCT_TO_CART.name().toLowerCase()));
        put(TypeRole.GUEST, Set.of(
                TypeCommandAsync.SEARCH_PRODUCT.name().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_RANDOM.name().toLowerCase(),
                TypeCommandAsync.GET_LIST_COLLECTION.name().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_BY_COLLECTION.name().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_FEEDBACK.name().toLowerCase(),
                TypeCommandAsync.GET_FRESH_NEWS.name().toLowerCase()));
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static AsyncCommandAccessMap getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new AsyncCommandAccessMap();
        }
        return INSTANCE;
    }
}
