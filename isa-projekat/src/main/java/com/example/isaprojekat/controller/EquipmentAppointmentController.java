package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.CompanyDTO;
import com.example.isaprojekat.dto.EquipmentAppointmentDTO;
import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.EquipmentAppointment;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.service.CompanyService;
import com.example.isaprojekat.service.EquipmentAppointmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/appointments")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EquipmentAppointmentController {
    @Autowired
    private EquipmentAppointmentService appointmentService;

    @GetMapping(value = "getById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<EquipmentAppointmentDTO> getCompany(@PathVariable Integer id) {

        EquipmentAppointment equipmentAppointment = appointmentService.findOne(id);

        if (equipmentAppointment == null) {
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new EquipmentAppointmentDTO(equipmentAppointment), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<EquipmentAppointmentDTO>> getAllAppointments() {

        List<EquipmentAppointment> appointments = appointmentService.findAll();

        // convert comapnies to DTOs
        List<EquipmentAppointmentDTO> appointmentDTOS = new ArrayList<>();
        for (EquipmentAppointment a : appointments) {
            appointmentDTOS.add(new EquipmentAppointmentDTO(a));
        }

        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }
    @PostMapping(value = "/availableDates")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<EquipmentAppointmentDTO>> GetAvailableAppointments(@RequestParam("items") String itemsJson) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        List<Item> items = objectMapper.readValue(itemsJson, new TypeReference<List<Item>>() {});
        List<EquipmentAppointment> appointments = appointmentService.findAvailableAppointments(items);

        // convert comapnies to DTOs
        List<EquipmentAppointmentDTO> appointmentDTOS = new ArrayList<>();
        for (EquipmentAppointment a : appointments) {
            appointmentDTOS.add(new EquipmentAppointmentDTO(a));
        }

        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<EquipmentAppointmentDTO> createAppointment(@RequestBody EquipmentAppointmentDTO equipmentAppointmentDTO) {
        try {
            EquipmentAppointment createdAppointment = appointmentService.createAppointment(equipmentAppointmentDTO);
            return new ResponseEntity<>(new EquipmentAppointmentDTO(createdAppointment), HttpStatus.CREATED);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@GetMapping(value = "/adminsAppointments/{admin_id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<EquipmentAppointmentDTO>> getAdminsAppointments(@PathVariable Integer admin_id) {

        List<EquipmentAppointment> appointments = appointmentService.findAllByAdminId(admin_id);

        // convert comapnies to DTOs
        List<EquipmentAppointmentDTO> appointmentDTOS = new ArrayList<>();
        for (EquipmentAppointment a : appointments) {
            appointmentDTOS.add(new EquipmentAppointmentDTO(a));
        }

        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }*/
}
