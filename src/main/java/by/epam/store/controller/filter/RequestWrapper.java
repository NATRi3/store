package by.epam.store.controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

public class RequestWrapper extends HttpServletRequestWrapper {
    private static final Logger log = LogManager.getLogger("RequestWrapper");
    private final Map<String,String> params = new HashMap<>();

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }
    @Override
    public String getParameter(String name){
        if(params.get(name)!=null){
            return params.get(name);
        }
        return super.getParameter(name);
    }

    public void setParameter(String name, String parameter){
        if(super.getParameter(name)==null||!super.getParameter(name).equals(parameter)) {
            params.put(name, parameter);
        }
    }
}
