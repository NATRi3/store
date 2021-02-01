package by.epam.store.controller.listner;

import by.epam.store.entity.User;
import by.epam.store.entity.type.TypeAccess;
import by.epam.store.entity.type.TypeRole;
import by.epam.store.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

@WebListener
public class SessionListener implements HttpSessionListener {
    public static final Logger logger = LogManager.getLogger(SessionListener.class);
    public static final User defaultUser =
            new User(-1,"default","default",new Date(), "default.jpg", TypeAccess.NONACTIVE,TypeRole.GUEST);
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(SessionAttribute.SERVER_TOKEN, new Random().nextInt(10000));
        session.setAttribute(SessionAttribute.USER, defaultUser);
        session.setAttribute(SessionAttribute.LOCALE, Locale.getDefault());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
       logger.info(se.getSession()+" destroyed");
    }
}
