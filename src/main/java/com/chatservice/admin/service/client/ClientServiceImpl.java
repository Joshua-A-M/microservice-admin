package com.chatservice.admin.service.client;


import com.chatservice.admin.DTO.EmployeeDTO;
import com.chatservice.admin.DTO.UserClientDTO;
import com.chatservice.admin.exception.ErrorCode;
import com.chatservice.admin.exception.PersonalIDNotFoundException;
import com.chatservice.admin.exception.UserDiscoveryClientException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{

    private final UserDiscoveryClient client;

    public ClientServiceImpl(UserDiscoveryClient client) {
        this.client = client;
    }

    @Override
    @CircuitBreaker(name = "employeeservice", fallbackMethod = "buildFallBackAdminList")
    @Bulkhead(name = "bulkheadEmployeeService", fallbackMethod = "buildFallBackAdminList")
    @RateLimiter(name = "employeeservice", fallbackMethod = "buildFallBackAdminList")
    @Retry(name = "retryEmployeeService", fallbackMethod = "buildFallBackAdminList")
    public UserClientDTO getUserInfo(String personalId) throws UserDiscoveryClientException, PersonalIDNotFoundException {
       return client.getUserInfo(personalId);
    }

    public List<EmployeeDTO> buildFallBackAdminList(String personalId){
        List<EmployeeDTO> fallbackList = new ArrayList<>();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId("0000000-00-0000");
        employeeDTO.setFirstname("First Name");
        fallbackList.add(employeeDTO);
        return fallbackList;
    }
}

