package by.epam.store.controller.filter;

import by.epam.store.command.TypeCommand;
import by.epam.store.command.TypeCommandAsync;
import by.epam.store.entity.type.TypeRole;

import java.util.HashMap;
import java.util.Set;

public class AsyncCommandAccessMap extends HashMap<TypeRole, Set<String>> {
    private static AsyncCommandAccessMap INSTANCE;

    private AsyncCommandAccessMap(){
        put(TypeRole.ADMIN, Set.of(
                TypeCommandAsync.SEARCH_PRODUCT.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_NEWS.toString().toLowerCase(),
                TypeCommandAsync.BLOCK_PRODUCT.toString().toLowerCase(),
                TypeCommandAsync.UNBLOCK_PRODUCT.toString().toLowerCase(),
                TypeCommandAsync.ACTIVATE_PRODUCT.toString().toLowerCase(),
                TypeCommandAsync.DEACTIVATE_PRODUCT.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_FEEDBACK.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_RANDOM.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_COLLECTION.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_BY_COLLECTION.toString().toLowerCase(),
                TypeCommandAsync.GET_FRESH_NEWS.toString().toLowerCase(),
                TypeCommandAsync.GET_IMAGE.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_USERS_BY_ROLE_STATUS.toString().toLowerCase()));
        put(TypeRole.MANAGER,Set.of(
                TypeCommandAsync.SEARCH_PRODUCT.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_RANDOM.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_FEEDBACK.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_COLLECTION.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_BY_COLLECTION.toString().toLowerCase(),
                TypeCommandAsync.GET_FRESH_NEWS.toString().toLowerCase(),
                TypeCommandAsync.GET_IMAGE.toString().toLowerCase(),
                TypeCommandAsync.DEACTIVATE_PRODUCT.toString().toLowerCase(),
                TypeCommandAsync.ADD_PRODUCT_TO_CART.toString().toLowerCase()));
        put(TypeRole.CLIENT,Set.of(
                TypeCommandAsync.SEARCH_PRODUCT.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_RANDOM.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_COLLECTION.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_BY_COLLECTION.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_FEEDBACK.toString().toLowerCase(),
                TypeCommandAsync.GET_FRESH_NEWS.toString().toLowerCase(),
                TypeCommandAsync.GET_IMAGE.toString().toLowerCase(),
                TypeCommandAsync.ADD_PRODUCT_TO_CART.toString().toLowerCase()));
        put(TypeRole.GUEST,Set.of(
                TypeCommandAsync.SEARCH_PRODUCT.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_RANDOM.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_COLLECTION.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_BY_COLLECTION.toString().toLowerCase(),
                TypeCommandAsync.GET_LIST_PRODUCT_FEEDBACK.toString().toLowerCase(),
                TypeCommandAsync.GET_FRESH_NEWS.toString().toLowerCase(),
                TypeCommandAsync.GET_IMAGE.toString().toLowerCase()));
    }

    public static AsyncCommandAccessMap getINSTANCE() {
        if(INSTANCE==null){
            INSTANCE = new AsyncCommandAccessMap();
        }
        return INSTANCE;
    }
}
