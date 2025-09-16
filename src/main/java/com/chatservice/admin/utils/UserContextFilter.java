package com.chatservice.admin.utils;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.stream.Collectors;

@Component
public class UserContextFilter implements Filter {
    //  Appends Outgoing Http Messages With Correlation Id
    private final static Logger logger = LogManager.getLogger(UserContextFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("\nUserContextFilter Accessed Successfully.");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        UserContextHolder.getContext()
                .setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));

        UserContextHolder.getContext()
                .setEmployeePid(httpServletRequest.getHeader(UserContext.EMPLOYEE_PID));
        UserContextHolder.getContext()
                .setUserPid(httpServletRequest.getHeader(UserContext.USER_PID));

        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();

        String headers = Collections.list(headerNames).stream()
                .map(name -> name + "=" + httpServletRequest.getHeader(name))
                .collect(Collectors.joining(", "));

        logger.info("\nThese are the headers attached currently: {}", headers);
        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }


    //  Adds Correlation Id To Outgoing Requests



}
