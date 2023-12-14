package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.CompanyDTO;
import com.example.isaprojekat.dto.EquipmentDTO;
import com.example.isaprojekat.dto.UserDTO;
import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.Equipment;
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

        // convert comapnies to DTOs
        List<CompanyDTO> companyDTO = new ArrayList<>();
        for (Company s : companies) {
            companyDTO.add(new CompanyDTO(s));
        }

        return new ResponseEntity<>(companyDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Integer id, @RequestBody CompanyDTO companyDTO) {
        try {
            Company updatedCompany = companyService.updateCompany(id, companyDTO);
            return new ResponseEntity<>(new CompanyDTO(updatedCompany), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/create")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO) {
        try {
            Company createdCompany = companyService.createCompany(companyDTO);
            return new ResponseEntity<>(new CompanyDTO(createdCompany), HttpStatus.CREATED);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getForAdmin/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CompanyDTO> getCompanyByAdminId(@PathVariable Integer id){
        try {
            Company company = companyService.findByAdminId(id);
            CompanyDTO companyDTO = new CompanyDTO();
            return new ResponseEntity<>(new CompanyDTO(company), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/search")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<CompanyDTO>> searchCompany(
            @RequestParam(required = false) String searchName,
            @RequestParam(required = false) String searchLocation
    )
    {
        List<Company> foundCompanies = new ArrayList<>();

        for (Company c : companyService.findAll()) {
            if ((searchName == null || c.getName().toLowerCase().contains(searchName.toLowerCase())) &&
                    (searchLocation == null || c.getAddress().toLowerCase().contains(searchLocation.toLowerCase()))) {
                foundCompanies.add(c);
            }
        }

        List<CompanyDTO> companyDTO = new ArrayList<>();
        for (Company c : foundCompanies) {
            companyDTO.add(new CompanyDTO(c));
        }

        return new ResponseEntity<>(companyDTO, HttpStatus.OK);
    }


}
