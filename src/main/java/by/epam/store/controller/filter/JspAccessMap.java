package by.epam.store.controller.filter;

import by.epam.store.model.entity.TypeRole;
import by.epam.store.controller.command.PagePath;

import java.util.HashMap;
import java.util.Set;

/**
 * The map which contains role and available jsp.
 */
public class JspAccessMap extends HashMap<TypeRole, Set<String>> {
    private static JspAccessMap INSTANCE;

    private JspAccessMap() {
        put(TypeRole.ADMIN,
                Set.of(PagePath.ACCOUNT, PagePath.MAIN, PagePath.SHOP,
                        PagePath.PAGE_404, PagePath.PAGE_500, PagePath.SINGLE_PRODUCT,
                        PagePath.ADMIN_PANEL, PagePath.ADMIN_PANEL_USERS, PagePath.ADMIN_PANEL_NEWS,
                        PagePath.ADMIN_PANEL_COLLECTION, PagePath.ADMIN_PANEL_ORDERS));
        put(TypeRole.CLIENT,
                Set.of(PagePath.ACCOUNT, PagePath.CART, PagePath.MAIN, PagePath.SHOP,
                        PagePath.PAGE_404, PagePath.PAGE_500, PagePath.SINGLE_PRODUCT));
        put(TypeRole.GUEST,
                Set.of(PagePath.LOGIN, PagePath.SHOP, PagePath.MAIN, PagePath.REGISTRATION,
                        PagePath.FORGOT_PASSWORD, PagePath.PAGE_404, PagePath.PAGE_500,
                        PagePath.SINGLE_PRODUCT));
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static JspAccessMap getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new JspAccessMap();
        }
        return INSTANCE;
    }
}
