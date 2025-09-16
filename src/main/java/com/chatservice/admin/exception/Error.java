package com.chatservice.admin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor
public class Error {

    private String code;
    private String message;
    private Integer status;

}
