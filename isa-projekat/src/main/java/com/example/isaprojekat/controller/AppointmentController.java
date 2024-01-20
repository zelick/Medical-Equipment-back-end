package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.AppointmentDTO;
import com.example.isaprojekat.enums.UserRole;
import com.example.isaprojekat.model.Appointment;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.model.User;
import com.example.isaprojekat.service.AppointmentService;
import com.example.isaprojekat.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/appointments")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "getById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<AppointmentDTO> getCompany(@PathVariable Integer id) {

        Appointment equipmentAppointment = appointmentService.findOne(id);

        if (equipmentAppointment == null) {
            //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AppointmentDTO(equipmentAppointment), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {

        List<Appointment> appointments = appointmentService.findAll();

        // convert comapnies to DTOs
        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();
        for (Appointment a : appointments) {
            appointmentDTOS.add(new AppointmentDTO(a));
        }

        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }

    /*
    @PostMapping(value = "/availableDates")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<AppointmentDTO>> GetAvailableAppointments(@RequestParam("items") String itemsJson) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        List<Item> items = objectMapper.readValue(itemsJson, new TypeReference<List<Item>>() {});
        List<Appointment> appointments = appointmentService.findAvailableAppointments(items);

        // convert comapnies to DTOs
        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();
        for (Appointment a : appointments) {
            appointmentDTOS.add(new AppointmentDTO(a));
        }

        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }
     */
    @DeleteMapping(value = "/delete/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> deleteAppointment(@PathVariable Integer id) {
        try {
            appointmentService.deleteById(id);
            return new ResponseEntity<>("Appointment deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Appointment not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the appointment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/create")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO equipmentAppointmentDTO, @RequestParam(name = "id", required = false) Integer userId) {
        User loggedInUser = userService.findOne(userId);

        if(loggedInUser!=null) {
            if (loggedInUser.getUserRole() != UserRole.COMPANY_ADMIN) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        try {
            Appointment createdAppointment = appointmentService.createAppointment(equipmentAppointmentDTO);
            return new ResponseEntity<>(new AppointmentDTO(createdAppointment), HttpStatus.CREATED);
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
