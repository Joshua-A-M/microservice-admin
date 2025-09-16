package com.chatservice.admin.controller.priv;

import com.chatservice.admin.DTO.EmployeeDTO;
import com.chatservice.admin.DTO.UserClientDTO;
import com.chatservice.admin.entity.MemberEntity;
import com.chatservice.admin.service.MemberService;
import com.chatservice.admin.service.client.ClientService;
import com.chatservice.admin.service.client.TemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController @RequestMapping(value = "/api/v1/private")
public class AdminController {

    private final MemberService service;
    private final ClientService clientService;
    private final TemplateService templateService;

    public AdminController(MemberService service, ClientService clientService, TemplateService templateService) {
        this.service = service;
        this.clientService = clientService;
        this.templateService = templateService;
    }

    @PostMapping(value = "/employee/new")
    public ResponseEntity<MemberEntity> createEmployee(@RequestBody EmployeeDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping(value = "/employee/{id}")
    public ResponseEntity<MemberEntity> getEmployeeById(@PathVariable Long id){
        MemberEntity member = service.findById(id);
        if(member != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(member);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping(value = "/employee/update")
    public ResponseEntity<MemberEntity> updateMEmployee(EmployeeDTO dto){
        MemberEntity member = service.update(dto);
        if (Objects.nonNull(member)){
            return ResponseEntity.ok(member);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(value = "/employee/delete")
    public ResponseEntity<?> deleteEmployeeByPersonalId(@RequestParam String personalId){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.deleteEmployeeByPersonalId(personalId));
    }

    @GetMapping(value = "/users/userInfo/client/{personalId}")
    public ResponseEntity<UserClientDTO> getUserClientInfo(@PathVariable String personalId){
        return ResponseEntity.status(HttpStatus.OK).body(clientService.getUserInfo(personalId));
    }

    @GetMapping(value = "/users/userInfo/template/{personalId}")
    public ResponseEntity<UserClientDTO> getUserTemplateInfo(@PathVariable String personalId){
        return ResponseEntity.status(HttpStatus.OK).body(templateService.getUserInfo(personalId));
    }

}
