package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.CompanyDTO;
import com.example.isaprojekat.dto.UserDTO;
import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.User;
import com.example.isaprojekat.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "api/companies")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "getById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Integer id) {

        Company company = companyService.findOne(id);
        System.out.println("Kompanija:");
        System.out.println(company);

        if (company == null) {
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CompanyDTO(company), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {

        List<Company> companies = companyService.findAll();

        // convert students to DTOs
        List<CompanyDTO> companyDTO = new ArrayList<>();
        for (Company s : companies) {
            companyDTO.add(new CompanyDTO(s));
        }

        return new ResponseEntity<>(companyDTO, HttpStatus.OK);
    }
}
