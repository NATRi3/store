package by.epam.store.command;

import by.epam.store.command.asyncimpl.ActivateProductCommand;
import by.epam.store.command.asyncimpl.AddProductCommand;
import by.epam.store.command.asyncimpl.DeactivateProductCommand;

public enum  TypeCommandAsync {
    DEACTIVATE_PRODUCT(new DeactivateProductCommand()),
    ADD_PRODUCT(new AddProductCommand()),
    ACTIVATE_PRODUCT(new ActivateProductCommand());
    private CommandAsync command;

    TypeCommandAsync(CommandAsync command) {
        this.command = command;
    }

    public CommandAsync getCommand(){
        return command;
    }
}
