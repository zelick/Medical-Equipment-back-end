package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.CompanyDTO;
import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.service.CompanyService;
import com.example.isaprojekat.service.UserService;
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
    private UserService userService;

    @GetMapping(value = "getById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Integer id) {
        Company company = companyService.findOne(id);
        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new CompanyDTO(company), HttpStatus.OK);
    }

    @GetMapping(value = "removeFrom/{companyId}/{equipmentId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CompanyDTO> removeEqFromCom(@PathVariable Integer companyId,
                                                      @PathVariable Integer equipmentId,
                                                      @RequestParam(name = "id", required = false) Integer userId) {
        if(!userService.isCompanyAdmin(userId)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Company company = companyService.removeEquipmentFromCompany(companyId, equipmentId);
        companyService.updateAverageGrade(companyId);

        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CompanyDTO(company), HttpStatus.OK);
    }

    @GetMapping(value = "addTo/{companyId}/{equipmentId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CompanyDTO> addEqToCom(@PathVariable Integer companyId,
                                                      @PathVariable Integer equipmentId
            , @RequestParam(name = "id", required = false) Integer userId) {
        if(!userService.isCompanyAdmin(userId)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Company company = companyService.addEquipmentToCompany(companyId, equipmentId);
        companyService.updateAverageGrade(companyId);

        if (company == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new CompanyDTO(company), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        List<Company> companies = companyService.findAll();
        List<CompanyDTO> companyDTO = new ArrayList<>();

        for (Company s : companies) {
            companyDTO.add(new CompanyDTO(s));
        }

        return new ResponseEntity<>(companyDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<CompanyDTO> updateCompany(@PathVariable Integer id, @RequestBody CompanyDTO companyDTO,@RequestParam(name = "id", required = false) Integer userId) {
        if(!userService.isCompanyAdmin(userId)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
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
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO,@RequestParam(name = "id", required = false) Integer userId) {
        if(!userService.isSystemAdmin(userId)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
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
            CompanyDTO companyDTO = new CompanyDTO(company);
            return new ResponseEntity<>(companyDTO, HttpStatus.OK);
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
        List<Company> foundCompanies = companyService.searchCompany(searchName, searchLocation);
        List<CompanyDTO> companyDTO = new ArrayList<>();

        for (Company c : foundCompanies) {
            companyDTO.add(new CompanyDTO(c));
        }

        return new ResponseEntity<>(companyDTO, HttpStatus.OK);
    }
}
