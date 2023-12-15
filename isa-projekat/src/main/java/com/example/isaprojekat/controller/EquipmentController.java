package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.CompanyDTO;
import com.example.isaprojekat.dto.EquipmentDTO;
import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.Equipment;
import com.example.isaprojekat.service.EquipmentService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
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

    /*@GetMapping(value = "/getEquipmentForCompany/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<EquipmentDTO>> getEquipmentForCompany(@PathVariable Integer id) {

        List<Equipment> equipment = equipmentService.findAllByCompanies_Id(id);

        // convert students to DTOs
        List<EquipmentDTO> equipmentDTO = new ArrayList<>();
        for (Equipment e : equipment) {
            equipmentDTO.add(new EquipmentDTO(e));
        }

        return new ResponseEntity<>(equipmentDTO, HttpStatus.OK);
    }*/

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
    ){
        List<Equipment> foundEquipments = new ArrayList<>();

        for (Equipment e : equipmentService.findAll()) {
            if (searchName == null || e.getName().toLowerCase().contains(searchName.toLowerCase())) {
                foundEquipments.add(e);
            }
        }

        List<EquipmentDTO> equipmentDTO = new ArrayList<>();
        for (Equipment e : foundEquipments) {
            equipmentDTO.add(new EquipmentDTO(e));
        }
        return new ResponseEntity<>(equipmentDTO, HttpStatus.OK);
    }
    @PutMapping(value = "/update/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<EquipmentDTO> updateEquipment(@PathVariable Integer id, @RequestBody EquipmentDTO equipmentDTO) {
        try {
            Equipment updatedEquipment = equipmentService.updateEquipment(id, equipmentDTO);
            return new ResponseEntity<>(new EquipmentDTO(updatedEquipment), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /*@GetMapping(value = "/getAllEquipmentWithCompanies")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<EquipmentDTO> getAllEquipmentWithCompanies() {
        List<Equipment> equipments = equipmentService.getAllEquipmentWithCompanies();
        List<EquipmentDTO> equipmentDTOs = new ArrayList<>();

        for (Equipment equipment : equipments) {
            equipmentDTOs.add(new EquipmentDTO(equipment));
        }
        return equipmentDTOs;
    }*/
}
