package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.UserDTO;
import com.example.isaprojekat.enums.UserRole;
import com.example.isaprojekat.model.User;
import com.example.isaprojekat.service.CompanyAdminService;
import com.example.isaprojekat.service.CompanyService;
import com.example.isaprojekat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "api/companyAdmins")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyAdminController {
    @Autowired
    private CompanyAdminService companyAdminService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/createAdmins/{companyId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> createCompanyAdminsForUserId(@RequestBody List<Integer> userIds, @PathVariable Integer companyId,@RequestParam(name = "id", required = false) Integer userId) {
        User loggedInUser = userService.findOne(userId);

        if(loggedInUser!=null){
            if(loggedInUser.getUserRole()!= UserRole.SYSTEM_ADMIN){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }}
        try {
            companyAdminService.createCompanyAdminsForUserId(userIds, companyId);
            return new ResponseEntity<>("Company admins created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create company admins", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getUsersNotInCompanyAdmin")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<UserDTO>> getUsersNotInCompanyAdmin() {
        try {
            List<UserDTO> usersNotInCompanyAdmin = companyAdminService.findUsersNotInCompanyAdmin();
            return new ResponseEntity<>(usersNotInCompanyAdmin, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
