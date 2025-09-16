package com.chatservice.admin.exception;

public class ErrorUtil {

    private ErrorUtil(){};

    public Error create(String errorCode, String errorMessageKey, Integer httpStatusCode){
        return new Error(errorCode, errorMessageKey, httpStatusCode);
    }
}
