package by.epam.store.controller.filter;

import by.epam.store.entity.type.TypeRole;
import by.epam.store.util.PagePath;

import java.util.HashMap;

public class JspAccessMap extends HashMap<TypeRole, String[]> {
    private static JspAccessMap INSTANCE;

    private JspAccessMap(){
        String[] adminJsp = new String[]{
                PagePath.ACCOUNT_PAGE,PagePath.CART_PAGE,PagePath.MAIN_PAGE
        };
        put(TypeRole.ADMIN,adminJsp);
        String[] userJsp = new String[]{
                PagePath.ACCOUNT_PAGE,PagePath.CART_PAGE,PagePath.MAIN_PAGE
        };
        put(TypeRole.CLIENT,userJsp);
        String[] storageJsp = new String[]{
                PagePath.ACCOUNT_PAGE,PagePath.CART_PAGE,PagePath.MAIN_PAGE
        };
        put(TypeRole.STORAGE,storageJsp);
        String[] guestJsp = new String[]{
                PagePath.LOGIN_PAGE,PagePath.ACTIVATION_PAGE,PagePath.SHOP_PAGE,PagePath.MAIN_PAGE,
                PagePath.REGISTRATION_PAGE
        };
        put(TypeRole.GUEST,guestJsp);
    }

    public static JspAccessMap getINSTANCE() {
        if(INSTANCE==null){
            INSTANCE = new JspAccessMap();
        }
        return INSTANCE;
    }
}
