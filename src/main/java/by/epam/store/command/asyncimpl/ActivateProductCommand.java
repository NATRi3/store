package by.epam.store.command.asyncimpl;

import by.epam.store.command.CommandAsync;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@Log4j2
public class ActivateProductCommand implements CommandAsync {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            Writer writer = response.getWriter();

            writer.write(0);
        } catch (IOException e) {
            log.error(e);
            //TODO
        }
    }
}
