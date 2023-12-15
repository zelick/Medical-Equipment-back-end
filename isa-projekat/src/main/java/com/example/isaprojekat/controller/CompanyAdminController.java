package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.CompanyDTO;
import com.example.isaprojekat.dto.UserDTO;
import com.example.isaprojekat.service.CompanyAdminService;
import com.example.isaprojekat.service.CompanyService;
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

    @GetMapping("/getCompanyForAdmin/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CompanyDTO> getCompanyForAdmin(@PathVariable Integer id){
        try{
            CompanyDTO company = companyAdminService.getCompanyForAdmin(id);
            return new ResponseEntity<>(company, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/createAdmins/{companyId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> createCompanyAdminsForUserId(@RequestBody List<Integer> userIds, @PathVariable Integer companyId) {
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
