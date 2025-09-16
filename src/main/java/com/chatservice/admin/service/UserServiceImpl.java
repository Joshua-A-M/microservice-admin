package com.chatservice.admin.service;

import com.chatservice.admin.DTO.UserClientDTO;
import com.chatservice.admin.entity.UserRedisClient;
import com.chatservice.admin.exception.CannotCreateResourceException;
import com.chatservice.admin.exception.ErrorCode;
import com.chatservice.admin.exception.PersonalIDNotFoundException;
import com.chatservice.admin.repository.UserRedisRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRedisRepository redisRepository;

    public UserServiceImpl(UserRedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    @Override
    public UserRedisClient create(UserClientDTO userClientDTO) {

        UserRedisClient user = new UserRedisClient();
        if (userClientDTO == null) {
            throw new CannotCreateResourceException(ErrorCode.CANNOT_CREATE_RESOURCE);
        }

        user.setId(userClientDTO.getId());
        user.setFirstname(userClientDTO.getFirstname());
        user.setLastname(userClientDTO.getLastname());
        user.setUsername(userClientDTO.getUsername());
        user.setEmail(userClientDTO.getEmail());
        user.setPersonalId(userClientDTO.getPersonalId());
        user.setPassword(userClientDTO.getPassword());
        return redisRepository.save(user);
    }

    @Override
    public UserClientDTO getUserByPersonalId(String personalId) {
        Optional<UserRedisClient> optionalUser = redisRepository.findByPersonalId(personalId);
        UserClientDTO client = new UserClientDTO();
        if(Objects.nonNull(optionalUser.get())){
            UserRedisClient user = optionalUser.get();
            client.setId(user.getId());
            client.setFirstname(user.getFirstname());
            client.setLastname(user.getLastname());
            client.setEmail(user.getEmail());
            client.setPersonalId(user.getPersonalId());
            client.setPersonalId(user.getPassword());
            return client;
        }
        throw new PersonalIDNotFoundException(ErrorCode.CANNOT_RESOLVE_PID);
    }
}
