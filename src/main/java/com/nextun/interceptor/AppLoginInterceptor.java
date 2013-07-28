package com.nextun.interceptor;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AppLoginInterceptor 
        extends HandlerInterceptorAdapter {
    private Logger logger = 
            Logger.getLogger(this.getClass().getName());
    
    public boolean preHandle(HttpServletRequest request,
                    HttpServletResponse response, 
                    Object handler) throws Exception {
        logger.info(
                handler.getClass().getName() + " 開始執行...");
        return true;
    }
    
    public void postHandle(HttpServletRequest request, 
                HttpServletResponse response, 
                Object handler, 
                ModelAndView modelAndView) throws Exception {
        logger.info(
                handler.getClass().getName() + " 執行完畢...");
    }
    
    public void afterCompletion(HttpServletRequest request,
                HttpServletResponse response, 
                Object handler, 
                Exception ex) throws Exception {
        logger.info("請求處理完畢...");
    }
}