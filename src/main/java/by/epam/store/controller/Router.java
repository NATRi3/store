package by.epam.store.controller;

public class Router {
    private final String page;
    private final boolean isRedirect;

    public static Router forwardTo(String page){
        return new Router(page,false);
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

