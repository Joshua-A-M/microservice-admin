package com.chatservice.admin.exception;

import lombok.Getter;

@Getter
public class UserDiscoveryClientException extends RuntimeException{

    private final String errorCode;
    private final String errorMessageKey;

    public UserDiscoveryClientException(ErrorCode code){
        super(code.getErrorMessageKey());
        this.errorCode = code.getErrorCode();
        this.errorMessageKey = code.getErrorMessageKey();
    }

    public UserDiscoveryClientException(String message){
        super(message);
        this.errorCode = ErrorCode.CANNOT_RESOLVE_EXTERNAL_REQUEST.getErrorCode();;
        this.errorMessageKey = ErrorCode.CANNOT_RESOLVE_EXTERNAL_REQUEST.getErrorMessageKey();
    }
}
