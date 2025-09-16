package com.chatservice.admin.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


@RedisHash("user")
@NoArgsConstructor
@AllArgsConstructor @Getter @Setter
public class UserRedisClient implements Serializable {



    @Id
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
