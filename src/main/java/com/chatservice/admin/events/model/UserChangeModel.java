package com.chatservice.admin.events.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserChangeModel {

    private String type;
    private String action;
    private String personalId;
    private String correlationId;

    public UserChangeModel(){
        super();
    }

    public UserChangeModel(String type, String action, String personalId, String correlationId){
        super();
        this.type =type;
        this.action = action;
        this.personalId = personalId;
        this.correlationId =correlationId;
    }
}
