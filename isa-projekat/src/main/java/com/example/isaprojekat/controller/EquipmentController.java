package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.EquipmentDTO;
import com.example.isaprojekat.enums.UserRole;
import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.Equipment;
import com.example.isaprojekat.model.User;
import com.example.isaprojekat.service.CompanyService;
import com.example.isaprojekat.service.EquipmentService;
import com.example.isaprojekat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/equipment")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;
    private CompanyService companyService;
    private UserService userService;

    @GetMapping(value = "/getEquipmentForCompany/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<EquipmentDTO>> getEquipmentForCompany(@PathVariable Integer id) {
        Company company = companyService.findOne(id);
        List<Equipment> equipment = equipmentService.findAllByCompanyId(company);

        List<EquipmentDTO> equipmentDTO = new ArrayList<>();
        for (Equipment e : equipment) {
            equipmentDTO.add(new EquipmentDTO(e));
        }

        return new ResponseEntity<>(equipmentDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<EquipmentDTO> getById(@PathVariable Integer id){
        try {
            Equipment equipment = equipmentService.findById(id);
            EquipmentDTO equipmentDTO = new EquipmentDTO(equipment);
            return new ResponseEntity<>(equipmentDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<EquipmentDTO>> getAllCompanies() {
        List<Equipment> equipments = equipmentService.findAll();

        List<EquipmentDTO> equipmentDTOS = new ArrayList<>();
        for (Equipment e : equipments) {
            equipmentDTOS.add(new EquipmentDTO(e));
        }

        return new ResponseEntity<>(equipmentDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/searchByName")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<EquipmentDTO>> searchEquipmentByName(
            @RequestParam(required = false) String searchName
    )
    {
        List<Equipment> foundEquipments = equipmentService.searchEquipmentByName(searchName);
        List<EquipmentDTO> equipmentDTO = new ArrayList<>();

        for (Equipment e : foundEquipments) {
            equipmentDTO.add(new EquipmentDTO(e));
        }

        return new ResponseEntity<>(equipmentDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<EquipmentDTO> updateEquipment(@PathVariable Integer id, @RequestBody EquipmentDTO equipmentDTO, @RequestParam(name = "id", required = false) Integer userId) {
        if(!userService.isCompanyAdmin(userId)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Equipment updatedEquipment = equipmentService.updateEquipment(id, equipmentDTO);
            return new ResponseEntity<>(new EquipmentDTO(updatedEquipment), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
