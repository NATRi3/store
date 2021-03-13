package by.epam.store.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class XSSProtectFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        RequestWrapper wrapper = new RequestWrapper(request);
        Map<String, String[]> enumeration = wrapper.getParameterMap();
        for (Map.Entry<String, String[]> entry : enumeration.entrySet()) {
            String parameter = entry.getValue()[0];
            if (parameter != null) {
                String newParameter = parameter.replaceAll("<", "&lt;").replaceAll(">", "&gt;").
                        replaceAll("\"", "&#34;").replaceAll("'", "&#39;");
                wrapper.setParameter(entry.getKey(), newParameter);
            }
        }
        filterChain.doFilter(wrapper, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
