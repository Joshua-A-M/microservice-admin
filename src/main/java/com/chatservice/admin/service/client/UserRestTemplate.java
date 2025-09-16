package com.chatservice.admin.service.client;


import com.chatservice.admin.DTO.UserClientDTO;
import com.chatservice.admin.service.UserService;
import com.chatservice.admin.utils.UserContext;
//import io.micrometer.tracing.Span;

import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.Span;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserRestTemplate {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Tracer tracer;

    private final UserService userService;

    private static final Logger logger = LogManager.getLogger(UserRestTemplate.class);

    public UserRestTemplate(UserService userService) {
        this.userService = userService;

    }

    public UserClientDTO getUserInfo(String personalId){
        logger.debug("In Employee Service.getUserInfo: {}", UserContext.getCorrelationId());

        UserClientDTO userClientDTO = checkRedisCache(personalId);
        if(userClientDTO != null){
            logger.debug("\nI have successfully retrieved a user {} from the redis cache: {}", personalId, userClientDTO);
            return userClientDTO;
        }

        logger.debug("\nUnable to locate user from the redis cache: {}", userClientDTO);

        ResponseEntity<UserClientDTO> restExchange = restTemplate.exchange(
                "http://GATEWAYSERVER:8083/api/v1/private/users/{personalId}",
                HttpMethod.GET,
                null,
                UserClientDTO.class, personalId
        );

        userClientDTO = restExchange.getBody();
        if(userClientDTO != null){
            cacheUserObject(userClientDTO);
        }
        return restExchange.getBody();
    };

    private UserClientDTO checkRedisCache(String userPersonalId){
        Span newSpan = (Span) tracer.startScopedSpan("readUserDataFromRedis");
        try {
           return userService.getUserByPersonalId(userPersonalId);
        } catch (Exception ex){
            logger.error("Error encountered while trying to retrieve organization{} check Redis Cache. Exeption {}",
                    userPersonalId, ex);
            return null;
        }
        finally {
            newSpan.tag("peer.service", "redis");
            newSpan.event("client received");
            newSpan.end();
        }
    }

    private void cacheUserObject(UserClientDTO userClientDTO){
        try{
            userService.create(userClientDTO);
        } catch (Exception ex){
            logger.error("Unable to cache organization {} in Redis. Exception {}", userClientDTO.getPersonalId(), ex);
        }
    }
}
