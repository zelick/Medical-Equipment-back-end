package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.*;
import com.example.isaprojekat.enums.UserRole;
import com.example.isaprojekat.model.*;
import com.example.isaprojekat.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;

@RestController
@RequestMapping(value = "api/reservations")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {
    @Autowired
    private AppointmentService reservationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyAdminService companyAdminService;

    @PostMapping(value = "/create")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<AppointmentDTO> createReservation(@RequestBody AppointmentDTO reservationDTO, @RequestParam(name = "id", required = false) Integer userId) {
        User loggedInUser = userService.findOne(userId);

        if(loggedInUser!=null) {
            if (loggedInUser.getUserRole() != UserRole.USER) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        try {
            Appointment createdReservation = reservationService.createReservation(reservationDTO);
            return new ResponseEntity<>(new AppointmentDTO(createdReservation), HttpStatus.CREATED);
        } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<AppointmentDTO>> getAllReservations() {

        List<Appointment> reservations = reservationService.findAll();

        // convert comapnies to DTOs
        List<AppointmentDTO> reservationDTOS = new ArrayList<>();
        for (Appointment a : reservations) {
            reservationDTOS.add(new AppointmentDTO(a));
        }

        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }
    @GetMapping(value = "/byUser/{username}")
    public ResponseEntity<List<AppointmentDTO>> getByUser(@PathVariable String username, @RequestParam(name = "id", required = false) Integer userId) {

        User user = userService.findOneByEmail(username);
        User loggedInUser = userService.findOne(userId);

        if(loggedInUser!=null) {
            if (loggedInUser.getUserRole() != UserRole.USER || !Objects.equals(loggedInUser.getEmail(), user.getEmail())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        List<Appointment> reservations = reservationService.GetAllReservationsForUser(user);
        List<AppointmentDTO> reservationsDTO = new ArrayList<>();
        for (Appointment r: reservations
             ) {
            AppointmentDTO reservationDTO = new AppointmentDTO(r);
            reservationsDTO.add(reservationDTO);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }
    @PutMapping(value = "/addReservationToItem/{itemId}/{reservationId}")
    public ResponseEntity<String> addReservationToItem(@PathVariable Integer itemId, @PathVariable Integer reservationId) {

        try {
            Item item = itemService.getById(itemId);
            reservationService.AddReservationToItem(item, reservationId);
            return new ResponseEntity<>("Reservation added to item successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add reservation to item.", HttpStatus.BAD_REQUEST);
        }
    }
    //public void AddReservationToItem(Item item, Integer reservationId){
    //public void sendReservationQRCodeByEmail(Integer reservationId, String recipientEmail)
    @PostMapping("/sendQrCode/{reservationId}/{recipientEmail}")
    public void sendReservationQRCode(@PathVariable Integer reservationId, @PathVariable String recipientEmail) {
        try {
            // Poziv metode sendReservationQRCodeByEmail unutar servisa
            reservationService.sendReservationQRCodeByEmail(reservationId, recipientEmail);
            // Dodatne akcije ili povratna vrednost po potrebi
        } catch (Exception e) {
            // Obrada izuzetaka
            e.printStackTrace();
            // Ili neki drugi način obrade greške
        }
    }

    //ovde fali autorizacija - ispaviti
    @GetMapping(value = "/getAdminsAppointmentReservation/{admin_id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<AppointmentDTO>> getAdminsAppointmentReservation(@PathVariable int admin_id){
        CompanyDTO companyDto = companyAdminService.getCompanyForAdmin(admin_id);
        Company company = companyService.findOne(companyDto.getId());
        List<AppointmentDTO> reservationsDTO = new ArrayList<AppointmentDTO>();
        Set<Integer> uniqueReservationIds = new HashSet<>();
        List<Item> allItems = itemService.findAll();
        for (Equipment e : company.getEquipments()) {
            for (Item i : allItems) {
                if (e.getId().equals(i.getEquipmentId())) {
                    int reservationId = i.getReservation();
                    // Check if the reservationId is unique
                    if (uniqueReservationIds.add(reservationId)) {
                        Appointment reservation = reservationService.getById(reservationId);
                        AppointmentDTO dto = new AppointmentDTO(reservation);
                        reservationsDTO.add(dto);
                    }
                }
            }
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }
}
