package com.chatservice.admin.repository;

import com.chatservice.admin.entity.UserRedisClient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRedisRepository extends CrudRepository<UserRedisClient, String> {
    public Optional<UserRedisClient> findByPersonalId(String personalId);
}
