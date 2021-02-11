package by.epam.store.controller.listner;

import by.epam.store.entity.Cart;
import by.epam.store.entity.User;
import by.epam.store.entity.type.TypeRole;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.util.SessionAttribute;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
@Log4j2
@WebListener
public class SessionListener implements HttpSessionListener {
    public static final User defaultUser =
            new User(-1,"default",TypeRole.GUEST,"default","default.jpg", TypeStatus.NONACTIVE,new Date());
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        Cart emptyCart = new Cart();
        session.setAttribute(SessionAttribute.CART,emptyCart);
        session.setAttribute(SessionAttribute.SERVER_TOKEN, new Random().nextInt(10000));
        session.setAttribute(SessionAttribute.USER, defaultUser);
        session.setAttribute(SessionAttribute.LOCALE, Locale.getDefault());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
       log.info(se.getSession()+" destroyed");
    }
}
