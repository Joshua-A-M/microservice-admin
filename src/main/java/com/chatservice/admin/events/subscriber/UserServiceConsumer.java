package com.chatservice.admin.events.subscriber;

import com.chatservice.admin.DTO.UserClientDTO;
import com.chatservice.admin.events.model.UserChangeModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class UserServiceConsumer {

    private static final Logger logger = LogManager.getLogger(UserServiceConsumer.class);

    @Bean
    public Consumer<UserChangeModel> userTopicSubscriber(){
        return userChangeModel -> {
            switch(userChangeModel.getAction()){
                case "GET" -> logger.debug("\nReceived a GET event from the UserService for user id: {} ", userChangeModel.getPersonalId());
                case "SAVE" -> logger.debug("\nReceived a SAVE event from the UserService for user id: {}", userChangeModel.getPersonalId());
                default -> logger.error("Received an UNKNOWN event type {} from the user service", userChangeModel.getType());
            }
        };
    }
}
