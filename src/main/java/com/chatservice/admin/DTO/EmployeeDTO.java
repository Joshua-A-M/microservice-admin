package com.chatservice.admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class EmployeeDTO {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
    private String personalId;


}
