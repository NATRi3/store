package by.epam.store.controller.command;

/**
 * The type Session attribute.
 */
public class SessionAttribute {
    /**
     * The session attribute names:
     */
    public static final String LOCALE = "currentLocale";
    public static final String USER = "currentUser";
    public static final String SERVER_TOKEN = "stoken";
    public static final String PAGE = "currentPage";
    public static final String CART = "cart";

    private SessionAttribute() {
    }
}
