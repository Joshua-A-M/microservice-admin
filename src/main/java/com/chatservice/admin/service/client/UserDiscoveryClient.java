package com.chatservice.admin.service.client;

import com.chatservice.admin.DTO.UserClientDTO;
import com.chatservice.admin.exception.ErrorCode;
import com.chatservice.admin.exception.PersonalIDNotFoundException;
import com.chatservice.admin.exception.UserDiscoveryClientException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class UserDiscoveryClient {
    //  Makes A Requests To The External Service (Service Discovery)

    private final static Logger logger = LogManager.getLogger(UserDiscoveryClient.class);

    @Autowired
    DiscoveryClient discoveryClient;


    //  Create Controller Method
    public UserClientDTO getUserInfo(String personalId){

        RestTemplate restTemplate = new RestTemplate();
        //  Need To Create The Request Uri
        //  Requires RestTemplate And DiscoveryClient

        //  Get Instances Of The External Services
        List<ServiceInstance> instances = discoveryClient.getInstances("userService");

        //  Create The URI From The Instances
        String serviceUri = String.format("%s/api/v1/private/users/%s", instances.get(0).getUri().toString(), personalId);
        if (instances == null){
            throw new UserDiscoveryClientException(ErrorCode.CANNOT_RESOLVE_EXTERNAL_REQUEST);
        }

        try {
            //  Create The Response That Displays The UserClientDTO
            ResponseEntity<UserClientDTO> restExchange =
                    restTemplate.exchange(
                    serviceUri, HttpMethod.GET, null, UserClientDTO.class, personalId
            );
            if(restExchange.getStatusCode().is4xxClientError()) {
                throw new PersonalIDNotFoundException(ErrorCode.CANNOT_RESOLVE_PID);
            }

            UserClientDTO body = restExchange.getBody();
            if(body == null){
                throw new UserDiscoveryClientException(ErrorCode.CANNOT_RESOLVE_EXTERNAL_REQUEST);
            }

            return restExchange.getBody();
        } catch (PersonalIDNotFoundException e){
            logger.info("Error Code: {}, Personal ID: {}, Stack Trace: {}", e.getErrorCode(), personalId, e);
            throw new PersonalIDNotFoundException(ErrorCode.CANNOT_RESOLVE_PID);
        } catch (UserDiscoveryClientException e) {
            logger.info("Error Code: {}, Personal ID: {}, Stack Trace: {}", e.getErrorCode(), personalId, e);
            throw new UserDiscoveryClientException(ErrorCode.CANNOT_RESOLVE_EXTERNAL_REQUEST);
        }
    }

}
