package by.epam.store.command;

import by.epam.store.command.async.*;

/**
 * The enum Type command async.
 */
public enum TypeCommandAsync {
    /**
     * The Delete feedback.
     */
    DELETE_FEEDBACK(new DeleteFeedbackCommand()),
    /**
     * The Search product.
     */
    SEARCH_PRODUCT(new SearchProductCommand()),
    /**
     * The Change user status.
     */
    CHANGE_USER_STATUS(new ChangeUserStatusCommand()),
    /**
     * The Block product.
     */
    BLOCK_PRODUCT(new BlockProductCommand()),
    /**
     * The Unblock product.
     */
    UNBLOCK_PRODUCT(new ChangeProductStatusCommand()),
    /**
     * The Get user orders.
     */
    GET_USER_ORDERS(new GetUserOrdersCommand()),
    /**
     * The Get list users by role status.
     */
    GET_LIST_USERS_BY_ROLE_STATUS(new GetListUsersByRoleAndStatus()),
    /**
     * The Get list product feedback.
     */
    GET_LIST_PRODUCT_FEEDBACK(new GetListProductFeedbackCommand()),
    /**
     * The Get list product random.
     */
    GET_LIST_PRODUCT_RANDOM(new GetListProductRandomCommand()),
    /**
     * The Get list collection.
     */
    GET_LIST_COLLECTION(new GetListProductCollectionsCommand()),
    /**
     * The Get list news.
     */
    GET_LIST_NEWS(new GetListNewsCommand()),
    /**
     * The Get fresh news.
     */
    GET_FRESH_NEWS(new GetFreshNewsCommand()),
    /**
     * The Get list product by collection.
     */
    GET_LIST_PRODUCT_BY_COLLECTION(new GetListProductByCollectionCommand()),
    /**
     * The Get list orders.
     */
    GET_LIST_ORDERS(new GetListOrdersCommand()),
    /**
     * The Deactivate product.
     */
    DEACTIVATE_PRODUCT(new DeactivateProductCommand()),
    /**
     * The Add product to cart.
     */
    ADD_PRODUCT_TO_CART(new AddProductToCartCommand()),
    /**
     * The Activate product.
     */
    ACTIVATE_PRODUCT(new ActivateProductCommand());
    private final CommandAsync command;

    TypeCommandAsync(CommandAsync command) {
        this.command = command;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public CommandAsync getCommand() {
        return command;
    }
}
