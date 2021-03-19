package by.epam.store.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Router.
 */
public class Router {
    private final String page;
    private final boolean isRedirect;

    private Router(String page, boolean isRedirect) {
        this.page = page;
        this.isRedirect = isRedirect;
    }

    /**
     * Generate forward to page Router.
     *
     * @param page the page
     * @return the router
     */
    public static Router forwardTo(String page) {
        return new Router(page, false);
    }

    /**
     * Generate redirect to page Router.
     *
     * @param page    the page
     * @param request the request
     * @return the redirect router
     */
    public static Router redirectTo(String page, HttpServletRequest request) {
        if (page.contains(request.getContextPath())) {
            return new Router(page, true);
        } else {
            return new Router(request.getContextPath() + page, true);
        }
    }

    /**
     * Gets page.
     *
     * @return the page
     */
    public String getPage() {
        return page;
    }


    /**
     * Is redirect boolean.
     *
     * @return the boolean
     */
    public boolean isRedirect() {
        return isRedirect;
    }
}

