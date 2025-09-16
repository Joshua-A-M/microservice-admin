package com.chatservice.admin.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EmployeeRepository <T, id> extends CrudRepository<T, id> {
}
