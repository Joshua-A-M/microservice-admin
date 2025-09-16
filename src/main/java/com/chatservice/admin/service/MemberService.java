package com.chatservice.admin.service;

import com.chatservice.admin.DTO.EmployeeDTO;
import com.chatservice.admin.entity.MemberEntity;
import com.chatservice.admin.exception.PersonalIDNotFoundException;

public interface MemberService {

    public MemberEntity create(EmployeeDTO dto);

    public MemberEntity findById(Long id);

    public boolean updateEmail(String personalId, String email);

    public MemberEntity update(EmployeeDTO dto);

    public boolean deleteEmployeeByPersonalId(String personalId) throws PersonalIDNotFoundException;
}
