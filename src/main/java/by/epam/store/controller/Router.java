package by.epam.store.controller;

public class Router {
    private final String page;
    private final boolean isRedirect;

    public static Router forwardTo(String page){
        if(!page.contains("/controller")) {
            return new Router(page, false);
        } else {
            return new Router(page, true);
        }
    }
    public static Router redirectTo(String page){
        return new Router(page,true);
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

