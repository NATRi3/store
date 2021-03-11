package by.epam.store.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class CommandProviderAsync {
    private final static Logger log = LogManager.getLogger(CommandProviderAsync.class);

    public static Optional<CommandAsync> commandDefine(String command) {
        Optional<CommandAsync> optionalCommand = Optional.empty();
        try {
            if (command != null) {
                CommandAsync commandAsync =
                        TypeCommandAsync.valueOf(command.toUpperCase()).getCommand();
                optionalCommand = Optional.of(commandAsync);
            }
        } catch (IllegalArgumentException e) {
            log.error(e);
        }
        return optionalCommand;
    }

}
