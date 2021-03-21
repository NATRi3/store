package by.epam.store.command;


import by.epam.store.command.impl.*;

/**
 * The enum Type command.
 */
public enum TypeCommand {
    /**
     * The Create feedback.
     */
    CREATE_FEEDBACK(new CreateFeedbackCommand()),
    /**
     * The Create order.
     */
    CREATE_ORDER(new CreateOrderCommand()),
    /**
     * The Create admin.
     */
    CREATE_ADMIN(new CreateAdminCommand()),
    /**
     * The Redirect to single product.
     */
    REDIRECT_TO_SINGLE_PRODUCT(new RedirectToSingleProductCommand()),
    /**
     * The Activation.
     */
    ACTIVATION(new ActivationCommand()),
    /**
     * The Registration.
     */
    REGISTRATION(new RegistrationCommand()),
    /**
     * The Login.
     */
    LOGIN(new LoginCommand()),
    /**
     * The Logout.
     */
    LOGOUT(new LogoutCommand()),
    /**
     * The Add collection.
     */
    ADD_COLLECTION(new AddCollectionCommand()),
    /**
     * The Add news.
     */
    ADD_NEWS(new AddNewsCommand()),
    /**
     * The Add product.
     */
    ADD_PRODUCT(new AddProductCommand()),
    /**
     * The Add amount product to cart.
     */
    ADD_AMOUNT_PRODUCT_TO_CART(new AddAmountProductToCartCommand()),
    /**
     * The Change news.
     */
    CHANGE_NEWS(new ChangeNewsCommand()),
    /**
     * The Change product.
     */
    CHANGE_PRODUCT(new ChangeProductCommand()),
    /**
     * The Change news image.
     */
    CHANGE_NEWS_IMAGE(new ChangeNewsImageCommand()),
    /**
     * The Change product image.
     */
    CHANGE_PRODUCT_IMAGE(new ChangeProductImageCommand()),
    /**
     * The Upload image.
     */
    UPLOAD_IMAGE(new UploadFileCommand()),
    /**
     * The Delete news.
     */
    DELETE_NEWS(new DeleteNewsCommand()),
    /**
     * The Remove product from cart.
     */
    REMOVE_PRODUCT_FROM_CART(new RemoveProductFromCartCommand()),
    /**
     * The Forgot password.
     */
    FORGOT_PASSWORD(new ForgotPasswordCommand()),
    /**
     * The Change locale.
     */
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    /**
     * The Error not found.
     */
    ERROR_NOT_FOUND(new NotFoundCommand());

    private final Command command;

    /**
     * Gets command.
     *
     * @return the command
     */
    public Command getCommand() {
        return command;
    }

    TypeCommand(Command command) {
        this.command = command;
    }
}
