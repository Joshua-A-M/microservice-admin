package com.chatservice.admin.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    CANNOT_CREATE_RESOURCE("001", "Cannot Create The Request Object."),
    CANNOT_RESOLVE_PID("002", "Cannot Validate Personal Id."),
    CANNOT_RESOLVE_EXTERNAL_REQUEST("003", "Cannot Complete External Request.");

    private final String errorCode;
    private final String errorMessageKey;


    ErrorCode(String errorCode, String errorMessageKey) {
        this.errorCode = errorCode;
        this.errorMessageKey = errorMessageKey;
    }


}
