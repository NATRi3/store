package by.epam.store.controller.listener;

import by.epam.store.model.entity.Cart;
import by.epam.store.model.entity.User;
import by.epam.store.model.entity.TypeRole;
import by.epam.store.model.entity.TypeStatus;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Locale;
import java.util.Random;

/**
 * The type Session listener.
 */
@WebListener
public class SessionListener implements HttpSessionListener {
    private static final Logger log = LogManager.getLogger(SessionListener.class);
    private final User defaultUser =
            User.builder().id(-1).role(TypeRole.GUEST).access(TypeStatus.NONACTIVE).build();
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        Cart emptyCart = new Cart();
        session.setAttribute(SessionAttribute.CART,emptyCart);
        session.setAttribute(SessionAttribute.SERVER_TOKEN, new Random().nextInt(10000));
        session.setAttribute(SessionAttribute.USER, defaultUser);
        session.setAttribute(SessionAttribute.LOCALE, Locale.getDefault());
        session.setAttribute(SessionAttribute.PAGE, PagePath.MAIN);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
       log.info(se.getSession()+" destroyed");
    }
}
