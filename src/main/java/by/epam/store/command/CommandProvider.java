package by.epam.store.command;

import lombok.extern.log4j.Log4j2;
import java.util.Optional;

@Log4j2
public class CommandProvider {

    public static Optional<Command> commandDefine(String command){
        Optional<Command> optionalCommand = Optional.empty();
        try {
            if(command!=null) {
                Command resultCommand =
                        TypeCommand.valueOf(command.toUpperCase()).getCommand();
                optionalCommand = Optional.of(resultCommand);
            }
        }catch (IllegalArgumentException e){
            log.error(e);
        }
        return optionalCommand;
    }

}
