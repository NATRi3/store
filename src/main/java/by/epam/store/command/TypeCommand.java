package by.epam.store.command;


import by.epam.store.command.impl.*;

public enum TypeCommand {
    GET_LIST_PRODUCT(new RedirectToShopCommand()),
    REDIRECT_TO_CART(new RedirectToCartCommand()),
    DELETE_ACCOUNT(new DeleteAccountCommand()),
    REDIRECT_TO_ACCOUNT(new RedirectToAccountCommand()),
    REDIRECT_TO_REGISTRATION(new RedirectToRegistrationCommand()),
    REDIRECT_TO_LOGIN(new RedirectToLoginCommand()),
    ACTIVATION(new ActivationCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    REGISTRATION(new RegistrationCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    UPLOAD_IMAGE(new UploadFileCommand());

    private Command command;

    TypeCommand(Command command) {
        this.command = command;
    }

    public Command getCommand(){
        return command;
    }
}
