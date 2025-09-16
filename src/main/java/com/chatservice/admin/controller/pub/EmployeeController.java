package com.chatservice.admin.controller.pub;

import com.chatservice.admin.entity.MemberEntity;
import com.chatservice.admin.service.MemberService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping(value = "/api/v1/public")
public class EmployeeController {

    private final MemberService service;

    public EmployeeController(MemberService service) {
        this.service = service;
    }

    @PatchMapping("employee/password")
    public ResponseEntity<?> updateEmail(@RequestParam String personalId, @RequestParam String email){

        if (service.updateEmail(personalId, email)) {
            return ResponseEntity.ok("Updated Email");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
