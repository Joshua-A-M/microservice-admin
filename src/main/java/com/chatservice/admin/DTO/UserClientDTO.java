package com.chatservice.admin.DTO;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserClientDTO {

    @Nullable
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    @Nullable
    private String personalId;
    @Nullable
    private String password;
}
