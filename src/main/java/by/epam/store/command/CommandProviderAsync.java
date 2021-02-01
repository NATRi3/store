package by.epam.store.command;

import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Log4j2
public class CommandProviderAsync {

    public static Optional<CommandAsync> commandDefine(String command) {
        Optional<CommandAsync> optionalCommand = Optional.empty();
        try {
            if(command!=null) {
                CommandAsync commandAsync =
                        TypeCommandAsync.valueOf(command.toUpperCase()).getCommand();
                optionalCommand = Optional.of(commandAsync);
            }
        }catch (IllegalArgumentException e){
            log.error(e);
        }
        return optionalCommand;
    }

}
