package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.AppointmentDTO;
import com.example.isaprojekat.dto.CompanyDTO;
import com.example.isaprojekat.dto.ItemDTO;
import com.example.isaprojekat.enums.ReservationStatus;
import com.example.isaprojekat.enums.UserRole;
import com.example.isaprojekat.model.*;
import com.example.isaprojekat.service.*;
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
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "api/appointments")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    private UserService userService;
    private CompanyAdminService companyAdminService;
    private ReservationService reservationService;

    @GetMapping(value = "companyAppointments/{companyId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<AppointmentDTO>> findCompanyAppointments(@PathVariable Integer companyId,@RequestParam(name = "id", required = false) Integer userId) {
        User loggedInUser = userService.findOne(userId);
        Date currentDate = new Date();
        List<Appointment> foundAppointments = new ArrayList<>();
        List<AppointmentDTO> foundAppointmentsDTO = new ArrayList<>();
        List<Appointment> appointments = appointmentService.findAll();
        List<Reservation> reservations = reservationService.findAll();

        for (Appointment a : appointments) {
            Company company = companyAdminService.getCompanyForAdmin(a.getAdminId());

            if (company != null && companyId.equals(company.getId())) {
                foundAppointments.add(a);
            }
        }

        List<Appointment> appointmentsToRemove = new ArrayList<>(foundAppointments);

        for (Reservation r : reservations) {
            for (Appointment a : foundAppointments) {
                if (a.getId().equals(r.getAppointment().getId()) && r.getStatus().equals(ReservationStatus.PENDING)) {
                    appointmentsToRemove.remove(a);
                }
                if (a.getId().equals(r.getAppointment().getId()) && r.getStatus().equals(ReservationStatus.CANCELED) && r.getUser().getId().equals(userId)){
                    appointmentsToRemove.remove(a);
                }
                if(a.getAppointmentDate().before(currentDate)){
                    appointmentsToRemove.remove(a);
                }

            }
        }

        for (Appointment a : appointmentsToRemove) {
            foundAppointmentsDTO.add(new AppointmentDTO(a));
        }

        return new ResponseEntity<>(foundAppointmentsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/addAdminToAppointment/{companyId}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<AppointmentDTO> addAdminToAppointment(@PathVariable Integer companyId, @RequestBody AppointmentDTO appointmentDTO)
    {
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

    @GetMapping(value = "/companyAvailableAppointments/{admin_id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<AppointmentDTO> getCompanyAvailableAppointments(@PathVariable Integer admin_id) {
        Company company = companyAdminService.getCompanyForAdmin(admin_id);
        Date currentDate = new Date();
        List<Appointment> foundAppointments = new ArrayList<>();
        List<AppointmentDTO> foundAppointmentsDTO = new ArrayList<>();
        List<Appointment> appointments = appointmentService.findAll();
        List<Reservation> reservations = reservationService.findAll();

        for (Appointment a : appointments) {
            if (company.getId().equals(companyAdminService.getCompanyForAdmin(a.getAdminId()).getId())) {
                foundAppointments.add(a);
            }
        }

        List<Appointment> appointmentsToRemove = new ArrayList<>(foundAppointments);

        for (Reservation r : reservations) {
            for (Appointment a : foundAppointments) {
                if (a.getId().equals(r.getAppointment().getId())) {
                    appointmentsToRemove.remove(a);
                }
                if(a.getAppointmentDate().before(currentDate)){
                    appointmentsToRemove.remove(a);
                }
            }
        }

        for (Appointment a : appointmentsToRemove) {
            foundAppointmentsDTO.add(new AppointmentDTO(a));
        }

        return foundAppointmentsDTO;
    }
}
