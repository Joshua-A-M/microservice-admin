package com.chatservice.admin.service;

import com.chatservice.admin.DTO.EmployeeDTO;
import com.chatservice.admin.entity.MemberEntity;
import com.chatservice.admin.exception.CannotCreateResourceException;
import com.chatservice.admin.exception.ErrorCode;
import com.chatservice.admin.exception.PersonalIDNotFoundException;
import com.chatservice.admin.repository.MemberRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService{

    private final Logger logger = LogManager.getLogger(MemberServiceImpl.class);

    private final MemberRepository repo;

    public MemberServiceImpl(MemberRepository repo) {
        this.repo = repo;
    }

    @Override
    public MemberEntity create(EmployeeDTO dto) throws CannotCreateResourceException {
        MemberEntity member = new MemberEntity();

        if (dto == null) {
            throw new CannotCreateResourceException(ErrorCode.CANNOT_CREATE_RESOURCE);
        }
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new CannotCreateResourceException("Email is required");
        }

        member.setFirstname(dto.getFirstname());
        member.setLastname(dto.getLastname());
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());

        try {
            logger.info("\nSuccessfully Created New Employee: \n{} {}/, \n{}", dto.getFirstname(), dto.getLastname(), dto.getPersonalId());
            return repo.save(member);

        } catch (CannotCreateResourceException e) {
            logger.info(" \nError Code: {}, \nDTO: {}, \nStack Trace: {} ", e.getErrorCode(), dto, e);
            throw new CannotCreateResourceException(ErrorCode.CANNOT_CREATE_RESOURCE);
        }


    }

    @Override
    public MemberEntity findById(Long id) {
        Optional<MemberEntity> optional = repo.findById(id);
        return optional.orElse(null);
    }

    @Override
    public boolean updateEmail(String personalId, String email) {
        if(repo.updateEmail(personalId, email) == 1){
            return true;
        }
        return false;

    }

    @Override
    public MemberEntity update(EmployeeDTO dto) {
        MemberEntity member = new MemberEntity();

        member.setFirstname(dto.getFirstname());
        member.setLastname(dto.getLastname());
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());

        return repo.save(member);
    }

    @Override
    public boolean deleteEmployeeByPersonalId(String personalId) throws PermissionDeniedDataAccessException {
        try {
            if (repo.deleteByPersonalId(personalId) < 1) {
                throw new PersonalIDNotFoundException(ErrorCode.CANNOT_RESOLVE_PID);
            }
            logger.info("\nSuccessfully Deleted User With Personal Id: {}", personalId);
            return true;
        } catch (PersonalIDNotFoundException e){
            throw new PersonalIDNotFoundException(e.getErrorCode());
        }
    }
}
