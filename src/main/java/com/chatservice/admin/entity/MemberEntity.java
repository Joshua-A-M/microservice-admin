package com.chatservice.admin.entity;

import com.chatservice.admin.entity.utility.GenerateValues;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Entity @Getter @Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "EMPLOYEE_ID")
public class MemberEntity extends EmployeeEntity{

    private String username;
    private String password;

    @Size(max = 10, message = "Cannot Exceed 10 Characters.")
    @NotNull
    @Column(unique = true, nullable = false)
    private String personalId;

    public MemberEntity(String firstname, String lastname, String email){
        super(firstname, lastname, email);

    }
    @PrePersist
    public void generateValues(){

        personalId = GenerateValues.generatePersonalId();
        username = GenerateValues.generateUsername(firstname, lastname);

    }
}
