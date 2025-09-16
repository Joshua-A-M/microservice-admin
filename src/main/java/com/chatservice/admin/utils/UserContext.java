package com.chatservice.admin.utils;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
    //  P O J O  H T T P  H E A D E R S  F O R  T H E  S E R V E R  W E B  E X C H A N G E

    //  transaction management exchange
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String EMPLOYEE_PID ="tmx-employee-id";
    public static final String USER_PID = "tmx-user-id";

    private static final ThreadLocal<String> correlationId = new ThreadLocal<>();
    private static final ThreadLocal<String> employeePid = new ThreadLocal<>();
    private static final ThreadLocal<String> userPid = new ThreadLocal<>();

    public static String getCorrelationId(){
        return correlationId.get();
    }

    public static String getEmployeePid(){
        return employeePid.get();
    }

    public static String getUserPid(){
        return userPid.get();
    }

    public static void setCorrelationId(String cid){
        correlationId.set(cid);
    }

    public static void setEmployeePid(String epid){
        employeePid.set(epid);
    }

    public static void setUserPid(String upid){
        userPid.set(upid);
    }
}
