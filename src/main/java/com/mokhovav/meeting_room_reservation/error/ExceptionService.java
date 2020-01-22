package com.mokhovav.meeting_room_reservation.error;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Service
public class ExceptionService {

    public String getLoggerMessage(HttpServletRequest req, Exception e) {
        String result =
                        e.getMessage() + "\n" +
                        "Method: " + req.getMethod() + "\n" +
                        "Request URL: " + req.getRequestURL() + "\n" +
                        "Protocol: " + req.getProtocol() + "\n" +
                        "PathInfo: " + req.getPathInfo() + "\n" +
                        "Remote Address: " + req.getRemoteAddr() + "\n" +
                        "Remote User: " + req.getRemoteUser();
        Enumeration enumeration = req.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = (String) enumeration.nextElement();
            String value = req.getHeader(name);
            result += "\n" + name + ": " + value;
        }
        return result;
    }

    public ModelAndView getModelAndView(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/main/resources/templates/errors/error.html");
        modelAndView.addObject("errorMessage", e.getMessage());
        return modelAndView;
    }
}
