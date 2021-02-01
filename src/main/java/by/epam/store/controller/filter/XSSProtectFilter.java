package by.epam.store.controller.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Enumeration;

public class XSSProtectFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Enumeration<String> enumeration = servletRequest.getAttributeNames();
        while (enumeration.hasMoreElements()){
            String parameterName = enumeration.nextElement();
            String parameter = servletRequest.getParameter(parameterName);
            if(parameter != null){
                String newParameter = parameter.replaceAll("<", "&lt;").replaceAll(">","&gt;");
                servletRequest.setAttribute(parameterName,newParameter);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
