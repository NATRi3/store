package by.epam.store.controller.filter;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * The type Request wrapper.
 */
public class RequestWrapper extends HttpServletRequestWrapper {
    private final Map<String, String> params = new HashMap<>();

    /**
     * Instantiates a new Request wrapper.
     *
     * @param request the request
     */
    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        if (params.get(name) != null) {
            return params.get(name);
        }
        return super.getParameter(name);
    }


    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> superParameters = super.getParameterMap();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            superParameters.put(entry.getKey(), new String[]{entry.getValue()});
        }
        return superParameters;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Enumeration<String> superEnumeration = super.getParameterNames();
        if(params.isEmpty()){
            return superEnumeration;
        }
        Vector<String> vector = new Vector<>(params.keySet());
        superEnumeration.asIterator().forEachRemaining((x)->{if(!vector.contains(x))vector.add(x);});
        return vector.elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        if (params.get(name) != null) {
            String[] superValues = super.getParameterValues(name);
            String[] strings = new String[superValues.length + 1];
            System.arraycopy(superValues, 0, strings, 0, superValues.length);
            strings[superValues.length + 1] = params.get(name);
            return strings;
        } else {
            return super.getParameterValues(name);
        }
    }

    /**
     * Sets parameter.
     *
     * @param name      the name
     * @param parameter the parameter
     */
    public void setParameter(String name, String parameter) {
        if (super.getParameter(name) == null || !super.getParameter(name).equals(parameter)) {
            params.put(name, parameter);
        }
    }
}
