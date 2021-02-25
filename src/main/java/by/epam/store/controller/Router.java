package by.epam.store.controller;

import by.epam.store.controller.filter.RequestWrapper;

import javax.servlet.http.HttpServletRequest;

public class Router {
    private final String page;
    private final boolean isRedirect;

    public static Router forwardTo(String page, HttpServletRequest request){
        /*if (page.contains("/controller")){
            String[] parameters = page.substring(page.indexOf("?")+1).split("&");
            RequestWrapper requestWrapper = (RequestWrapper) request;
            for(String parameter: parameters){
                requestWrapper.setParameter(parameter.substring(0,parameter.indexOf("=")),parameter.substring(parameter.indexOf("=")+1));
            }
        }//todo*/
        return new Router(page, false);
    }

    public static Router redirectTo(String page, HttpServletRequest request){
        if(page.contains(request.getContextPath())) {
            return new Router(page, true);
        } else {
            return new Router(request.getContextPath()+page,true);
        }
    }

    private Router(String page,boolean isRedirect){
        this.page = page;
        this.isRedirect = isRedirect;
    }

    public String getPage() {
        return page;
    }


    public boolean isRedirect() {
        return isRedirect;
    }
}

