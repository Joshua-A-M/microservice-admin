package com.chatservice.admin.exception;

import lombok.Getter;

@Getter
public class PersonalIDNotFoundException extends RuntimeException {

    private final String errorCode;
    private final String errorMessageKey;

    public PersonalIDNotFoundException(ErrorCode code){
        super(code.getErrorMessageKey());
        this.errorCode = code.getErrorCode();
        this.errorMessageKey = code.getErrorMessageKey();
    }

    public PersonalIDNotFoundException(String message){
        super(message);
        this.errorCode = ErrorCode.CANNOT_RESOLVE_PID.getErrorCode();
        this.errorMessageKey = ErrorCode.CANNOT_RESOLVE_PID.getErrorMessageKey();
    }
}
