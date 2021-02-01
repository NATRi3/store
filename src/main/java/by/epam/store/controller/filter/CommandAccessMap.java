package by.epam.store.controller.filter;

import by.epam.store.command.TypeCommand;
import by.epam.store.entity.type.TypeRole;
import java.util.HashMap;

public class CommandAccessMap extends HashMap<TypeRole,String[]> {
    private static CommandAccessMap INSTANCE;

    private CommandAccessMap(){
        String[] adminCommand = new String[]{
                TypeCommand.CHANGE_LOCALE.toString().toLowerCase(),
                TypeCommand.DELETE_ACCOUNT.toString().toLowerCase(),
                TypeCommand.LOGOUT.toString().toLowerCase(),
                TypeCommand.REDIRECT_TO_ACCOUNT.toString().toLowerCase(),
                TypeCommand.REDIRECT_TO_CART.toString().toLowerCase(),
                TypeCommand.GET_LIST_PRODUCT.toString().toLowerCase(),
                TypeCommand.ACTIVATION.toString().toLowerCase()
        };
        put(TypeRole.ADMIN,adminCommand);
        String[] storageCommand = new String[]{
                TypeCommand.CHANGE_LOCALE.toString().toLowerCase(),
                TypeCommand.LOGOUT.toString().toLowerCase(),
                TypeCommand.REDIRECT_TO_ACCOUNT.toString().toLowerCase(),
                TypeCommand.REDIRECT_TO_CART.toString().toLowerCase(),
                TypeCommand.GET_LIST_PRODUCT.toString().toLowerCase(),
                TypeCommand.ACTIVATION.toString().toLowerCase()
        };
        put(TypeRole.STORAGE,storageCommand);
        String[] userCommand = new String[]{
                TypeCommand.CHANGE_LOCALE.toString().toLowerCase(),
                TypeCommand.LOGOUT.toString().toLowerCase(),
                TypeCommand.REDIRECT_TO_ACCOUNT.toString().toLowerCase(),
                TypeCommand.REDIRECT_TO_CART.toString().toLowerCase(),
                TypeCommand.GET_LIST_PRODUCT.toString().toLowerCase(),
                TypeCommand.ACTIVATION.toString().toLowerCase()
        };
        put(TypeRole.CLIENT,userCommand);
        String[] guestCommand = new String[]{
                TypeCommand.CHANGE_LOCALE.toString().toLowerCase(),
                TypeCommand.DELETE_ACCOUNT.toString().toLowerCase(),
                TypeCommand.LOGIN.toString().toLowerCase(),
                TypeCommand.REGISTRATION.toString().toLowerCase(),
                TypeCommand.REDIRECT_TO_LOGIN.toString().toLowerCase(),
                TypeCommand.REDIRECT_TO_REGISTRATION.toString().toLowerCase(),
                TypeCommand.GET_LIST_PRODUCT.toString().toLowerCase(),
                TypeCommand.ACTIVATION.toString().toLowerCase()
        };
        put(TypeRole.GUEST,guestCommand);
    }

    public static CommandAccessMap getINSTANCE() {
        if(INSTANCE==null){
            INSTANCE = new CommandAccessMap();
        }
        return INSTANCE;
    }
}
