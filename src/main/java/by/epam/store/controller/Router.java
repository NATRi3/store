package by.epam.store.controller;

import javax.servlet.http.HttpServletRequest;

public class Router {
    private final String page;
    private final boolean isRedirect;

    private Router(String page, boolean isRedirect) {
        this.page = page;
        this.isRedirect = isRedirect;
    }

    public static Router forwardTo(String page, HttpServletRequest request) {
        return new Router(page, false);
    }

    public static Router redirectTo(String page, HttpServletRequest request) {
        if (page.contains(request.getContextPath())) {
            return new Router(page, true);
        } else {
            return new Router(request.getContextPath() + page, true);
        }
    }

    public String getPage() {
        return page;
    }


    public boolean isRedirect() {
        return isRedirect;
    }
}

