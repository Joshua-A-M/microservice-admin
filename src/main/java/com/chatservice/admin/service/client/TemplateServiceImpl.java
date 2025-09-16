package com.chatservice.admin.service.client;

import com.chatservice.admin.DTO.UserClientDTO;
import com.chatservice.admin.utils.UserContextHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImpl implements TemplateService{

    private static final Logger logger = LogManager.getLogger(TemplateServiceImpl.class);

    private final UserRestTemplate restTemplate;

    public TemplateServiceImpl(UserRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserClientDTO getUserInfo(String personalId) {
        logger.info("\nSuccessfully Retrieved User With Personal Id: {}.", personalId);
        return restTemplate.getUserInfo(personalId);

    }
}
