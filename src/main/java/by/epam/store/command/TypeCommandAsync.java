package by.epam.store.command;

import by.epam.store.command.async.*;

public enum  TypeCommandAsync {
    DELETE_FEEDBACK(new DeleteFeedbackCommand()),
    SEARCH_PRODUCT(new SearchProductCommand()),
    BLOCK_PRODUCT(new BlockProductCommand()),
    UNBLOCK_PRODUCT(new UnblockProductCommand()),
    GET_USER_ORDERS(new GetUserOrdersCommand()),
    GET_LIST_USERS_BY_ROLE_STATUS(new GetListUsersByRoleAndStatus()),
    GET_LIST_PRODUCT_FEEDBACK(new GetListProductFeedbackCommand()),
    GET_LIST_PRODUCT_RANDOM(new GetListProductRandomCommand()),
    GET_LIST_COLLECTION(new GetListProductCollectionsCommand()),
    GET_LIST_NEWS(new GetListNewsCommand()),
    GET_FRESH_NEWS(new GetFreshNewsCommand()),
    GET_LIST_PRODUCT_BY_COLLECTION(new GetListProductByCollectionCommand()),
    DEACTIVATE_PRODUCT(new DeactivateProductCommand()),
    ADD_PRODUCT_TO_CART(new AddProductToCartCommand()),
    ACTIVATE_PRODUCT(new ActivateProductCommand());
    private CommandAsync command;

    TypeCommandAsync(CommandAsync command) {
        this.command = command;
    }

    public CommandAsync getCommand(){
        return command;
    }
}
