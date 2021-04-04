package by.epam.store.controller.command.impl;

import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.controller.Router;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * The type Router response helper.
 */
public class RouterResponseHelper {
    private static final String successfulMessagePrefix = "successful";

    /**
     * Generate router from parameters
     *
     * @param request    the request
     * @param message    the message
     * @param parameters the parameters
     * @param page       the page
     * @return the router
     */
    public static Router router(HttpServletRequest request,
                                String message,
                                Map<String, String[]> parameters,
                                String page) {
        return router(request, message, parameters, page, page);
    }

    /**
     * Generate router from parameters
     *
     * @param request        the request
     * @param message        the message
     * @param parameters     the parameters
     * @param errorPage      the error page
     * @param successfulPage the successful page
     * @return the router
     */
    public static Router router(HttpServletRequest request,
                                String message,
                                Map<String, String[]> parameters,
                                String errorPage,
                                String successfulPage) {
        request.setAttribute(RequestParameterAndAttribute.MESSAGE, message);
        if (!message.contains(successfulMessagePrefix)) {
            writeMapToRequest(request, parameters);
            return Router.forwardTo(errorPage);
        } else {
            return Router.redirectTo(successfulPage, request);
        }
    }

    /**
     * Generate router from parameters
     *
     * @param request         the request
     * @param optionalMessage the optional message
     * @param page            the page
     * @return the router
     */
    public static Router router(HttpServletRequest request,
                                Optional<String> optionalMessage,
                                String page) {
        return router(request, optionalMessage, Collections.emptyMap(), page, page);
    }

    /**
     * Generate router from parameters
     *
     * @param request         the request
     * @param optionalMessage the optional message
     * @param errorPage       the error page
     * @param successfulPage  the successful page
     * @return the router
     */
    public static Router router(HttpServletRequest request,
                                Optional<String> optionalMessage,
                                String errorPage,
                                String successfulPage) {
        return router(request, optionalMessage, Collections.emptyMap(), errorPage, successfulPage);
    }

    /**
     * Generate router from parameters
     *
     * @param request         the request
     * @param optionalMessage the optional message
     * @param parameters      the parameters
     * @param page            the page
     * @return the router
     */
    public static Router router(HttpServletRequest request,
                                Optional<String> optionalMessage,
                                Map<String, String[]> parameters,
                                String page) {
        return router(request, optionalMessage, parameters, page, page);
    }

    /**
     * Generate router from parameters
     *
     * @param request         the request
     * @param optionalMessage the optional message
     * @param parameters      the parameters
     * @param errorPage       the error page
     * @param successfulPage  the successful page
     * @return the router
     */
    public static Router router(HttpServletRequest request,
                                Optional<String> optionalMessage,
                                Map<String, String[]> parameters,
                                String errorPage,
                                String successfulPage) {
        if (optionalMessage.isPresent()) {
            request.setAttribute(RequestParameterAndAttribute.MESSAGE, optionalMessage.get());
            writeMapToRequest(request, parameters);
            return Router.forwardTo(errorPage);
        } else {
            return Router.redirectTo(successfulPage, request);
        }
    }

    /**
     * Generate router from parameters
     *
     * @param request the request
     * @param message the message
     * @param page    the page
     * @return the router
     */
    public static Router router(HttpServletRequest request,
                                String message,
                                String page) {
        return router(request, message, page, page);
    }

    /**
     * Generate router from parameters
     *
     * @param request        the request
     * @param message        the message
     * @param errorPage      the error page
     * @param successfulPage the successful page
     * @return the router
     */
    public static Router router(HttpServletRequest request,
                                String message,
                                String errorPage,
                                String successfulPage) {
        if (message.contains(successfulMessagePrefix)) {
            request.setAttribute(RequestParameterAndAttribute.MESSAGE, message);
            return Router.forwardTo(successfulPage);
        } else {
            request.setAttribute(RequestParameterAndAttribute.MESSAGE, message);
            return Router.forwardTo(errorPage);
        }
    }

    private static void writeMapToRequest(HttpServletRequest request,
                                          Map<String, String[]> map) {
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue()[0]);
            if (entry.getValue().length > 1 && RequestUtil.replaceFields.containsValue(entry.getKey())) {
                for(Map.Entry<String,String> e : RequestUtil.replaceFields.entrySet()){
                    if(e.getValue().equals(entry.getKey())){
                        request.setAttribute(e.getKey().toLowerCase(),entry.getValue()[1]);
                    }
                }
            }
        }
    }
}
