package com.mokhovav.meeting_room_reservation.error;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    private final ExceptionService exceptionService;
    private final Logger logger;

    public ExceptionController(Logger logger, ExceptionService exceptionService) {
        this.logger = logger;
        this.exceptionService = exceptionService;
    }

    @ExceptionHandler(CustomException.class)
    public void customExceptionHandler(HttpServletRequest req, CustomException e){
        return;
    }

    @ExceptionHandler(CriticalException.class)
    public ModelAndView criticalExceptionHandler(HttpServletRequest req, CriticalException e){
        logger.error("CRITICAL: "+exceptionService.getLoggerMessage(req, e));
        return exceptionService.getModelAndView(e);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView criticalExceptionHandler(HttpServletRequest req, Exception e){
        logger.error("UNKNOWN: "+exceptionService.getLoggerMessage(req, e));
        return exceptionService.getModelAndView(e);
    }
}