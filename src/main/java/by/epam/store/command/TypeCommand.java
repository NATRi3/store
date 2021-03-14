package by.epam.store.command;


import by.epam.store.command.impl.*;

public enum TypeCommand {
    CREATE_FEEDBACK(new CreateFeedbackCommand()),
    CREATE_ORDER(new CreateOrderCommand()),
    REDIRECT_TO_SINGLE_PRODUCT(new RedirectToSingleProductCommand()),
    ACTIVATION(new ActivationCommand()),
    REGISTRATION(new RegistrationCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    ADD_COLLECTION(new AddCollectionCommand()),
    ADD_NEWS(new AddNewsCommand()),
    ADD_PRODUCT(new AddProductCommand()),
    ADD_AMOUNT_PRODUCT_TO_CART(new AddAmountProductToCartCommand()),
    CHANGE_NEWS(new ChangeNewsCommand()),
    CHANGE_PRODUCT(new ChangeProductCommand()),
    CHANGE_NEWS_IMAGE(new ChangeNewsImageCommand()),
    CHANGE_PRODUCT_IMAGE(new ChangeProductImageCommand()),
    UPLOAD_IMAGE(new UploadFileCommand()),
    DELETE_NEWS(new DeleteNewsCommand()),
    REMOVE_PRODUCT_FROM_CART(new RemoveProductFromCartCommand()),
    FORGOT_PASSWORD(new ForgotPasswordCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    ERROR_NOT_FOUND(new NotFoundCommand());

    private final Command command;

    public Command getCommand() {
        return command;
    }

    TypeCommand(Command command) {
        this.command = command;
    }
}
