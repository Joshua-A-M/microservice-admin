package com.chatservice.admin.repository;

import com.chatservice.admin.entity.MemberEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

public interface MemberRepository extends EmployeeRepository<MemberEntity, Long> {

    @Transactional @Modifying
    @Query("Update MemberEntity m Set m.email = :email Where m.personalId = :personalId")
    public int updateEmail(@Param("personalId") String personalId, @Param("email") String email);

    @Modifying @Transactional
    @Query("Delete from MemberEntity m Where m.personalId = :personalId")
    public int deleteByPersonalId(@Param("personalId") String personalId);
}
