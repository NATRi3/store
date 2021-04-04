package by.epam.store.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * The type Command provider.
 */
public class CommandProvider {
    private final static Logger log = LogManager.getLogger(CommandProvider.class);

    /**
     * Command define optional.
     *
     * @param command the command
     * @return the optional
     */
    public static Optional<Command> commandDefine(String command) {
        Optional<Command> optionalCommand = Optional.empty();
        try {
            if (command != null) {
                Command resultCommand =
                        TypeCommand.valueOf(command.toUpperCase()).getCommand();
                optionalCommand = Optional.of(resultCommand);
            }
        } catch (IllegalArgumentException e) {
            log.error(e);
        }
        return optionalCommand;
    }

}
