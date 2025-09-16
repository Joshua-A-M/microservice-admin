package com.chatservice.admin.service;

import com.chatservice.admin.DTO.UserClientDTO;
import com.chatservice.admin.entity.UserRedisClient;

public interface UserService {

    public UserRedisClient create(UserClientDTO userClientDTO);
    public UserClientDTO getUserByPersonalId(String personalId);
}
