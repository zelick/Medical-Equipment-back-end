package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.AppointmentDTO;
import com.example.isaprojekat.model.*;
import com.example.isaprojekat.service.*;
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
    private UserService userService;

    @GetMapping(value = "companyAppointments/{companyId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<AppointmentDTO>> findCompanyAppointments(@PathVariable Integer companyId,@RequestParam(name = "id", required = false) Integer userId) {
        if(!userService.isUser(userId)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<AppointmentDTO> foundAppointmentsDTO = new ArrayList<>();
        List<Appointment> foundAppointments = appointmentService.findCompanyAppointments(companyId, userId);
        for (Appointment a : foundAppointments) {
            foundAppointmentsDTO.add(new AppointmentDTO(a));
        }
        return new ResponseEntity<>(foundAppointmentsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/addAdminToAppointment/{companyId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<AppointmentDTO> addAdminToAppointment(@PathVariable Integer companyId, @RequestBody AppointmentDTO appointmentDTO, @RequestParam(name = "id", required = false) Integer userId)
    {
        if(!userService.isUser(userId)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Appointment updatedAppointment = appointmentService.addAdminToAppointment(appointmentDTO, companyId);
            return new ResponseEntity<>(new AppointmentDTO(updatedAppointment), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "getById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<AppointmentDTO> getCompany(@PathVariable Integer id) {
        Appointment equipmentAppointment = appointmentService.findOne(id);
        return new ResponseEntity<>(new AppointmentDTO(equipmentAppointment), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.findAll();
        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();
        for (Appointment a : appointments) {
            appointmentDTOS.add(new AppointmentDTO(a));
        }

        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }
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
        if(!userService.isCompanyAdmin(userId)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Appointment createdAppointment = appointmentService.createAppointment(equipmentAppointmentDTO);
            return new ResponseEntity<>(new AppointmentDTO(createdAppointment), HttpStatus.CREATED);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/companyAvailableAppointments/{admin_id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<AppointmentDTO> getCompanyAvailableAppointments(@PathVariable Integer admin_id) {
        List<Appointment> foundAppointments = appointmentService.getCompanyAvailableAppointments(admin_id);
        List<AppointmentDTO> foundAppointmentsDTO = new ArrayList<>();
        for (Appointment a : foundAppointments) {
            foundAppointmentsDTO.add(new AppointmentDTO(a));
        }
        return foundAppointmentsDTO;
    }

    @PutMapping(value = "/update")
    @CrossOrigin(origins = "http://localhost:4200")
    public AppointmentDTO expireReservation(@RequestBody AppointmentDTO appointmentDTO) {
        try {
            Appointment foundAppointment = appointmentService.findOne(appointmentDTO.getId());
            Appointment updatedAppointment = appointmentService.update(foundAppointment);
            return new AppointmentDTO(updatedAppointment);
        } catch (Exception e) {
            return null;
        }
    }
}