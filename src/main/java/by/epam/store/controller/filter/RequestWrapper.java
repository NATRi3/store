package by.epam.store.controller.filter;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class RequestWrapper extends HttpServletRequestWrapper {
    private static final Logger log = LogManager.getLogger("RequestWrapper");
    private final Map<String, String> params = new HashMap<>();

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
        Iterator<String> superEnumeration = super.getParameterNames().asIterator();
        Vector<String> vector = new Vector<String>(params.keySet());
        superEnumeration.forEachRemaining(vector::add);
        return vector.elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        List<String> superValues = Arrays.asList(super.getParameterValues(name));
        Collection<String> collection = params.values();
        collection.addAll(superValues);
        return collection.toArray(new String[0]);
    }

    public void setParameter(String name, String parameter) {
        if (super.getParameter(name) == null || !super.getParameter(name).equals(parameter)) {
            params.put(name, parameter);
        }
    }
}
