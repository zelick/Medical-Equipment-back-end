package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.AppointmentReservationDTO;
import com.example.isaprojekat.dto.EquipmentAppointmentDTO;
import com.example.isaprojekat.dto.ItemDTO;
import com.example.isaprojekat.dto.UserDTO;
import com.example.isaprojekat.enums.UserRole;
import com.example.isaprojekat.model.AppointmentReservation;
import com.example.isaprojekat.model.EquipmentAppointment;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.model.User;
import com.example.isaprojekat.service.AppointmentReservationService;
import com.example.isaprojekat.service.ItemService;
import com.example.isaprojekat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "api/reservations")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentReservationController {
    @Autowired
    private AppointmentReservationService reservationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemService;

    @PostMapping(value = "/create")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<AppointmentReservationDTO> createReservation(@RequestBody AppointmentReservationDTO reservationDTO,@RequestParam(name = "id", required = false) Integer userId) {
        User loggedInUser = userService.findOne(userId);

        if(loggedInUser!=null) {
            if (loggedInUser.getUserRole() != UserRole.USER) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        try {
            AppointmentReservation createdReservation = reservationService.createReservation(reservationDTO);
            return new ResponseEntity<>(new AppointmentReservationDTO(createdReservation), HttpStatus.CREATED);
        } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<AppointmentReservationDTO>> getAllReservations() {

        List<AppointmentReservation> reservations = reservationService.findAll();

        // convert comapnies to DTOs
        List<AppointmentReservationDTO> reservationDTOS = new ArrayList<>();
        for (AppointmentReservation a : reservations) {
            reservationDTOS.add(new AppointmentReservationDTO(a));
        }

        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }
    @GetMapping(value = "/byUser/{username}")
    public ResponseEntity<List<AppointmentReservationDTO>> getByUser(@PathVariable String username,@RequestParam(name = "id", required = false) Integer userId) {

        User user = userService.findOneByEmail(username);
        User loggedInUser = userService.findOne(userId);

        if(loggedInUser!=null) {
            if (loggedInUser.getUserRole() != UserRole.USER || !Objects.equals(loggedInUser.getEmail(), user.getEmail())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        List<AppointmentReservation> reservations = reservationService.GetAllReservationsForUser(user);
        List<AppointmentReservationDTO> reservationsDTO = new ArrayList<>();
        for (AppointmentReservation r: reservations
             ) {
            AppointmentReservationDTO reservationDTO = new AppointmentReservationDTO(r);
            reservationsDTO.add(reservationDTO);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }
    @PutMapping(value = "/addReservationToItem/{itemId}/{reservationId}")
    public ResponseEntity<String> addReservationToItem(@PathVariable Integer itemId, @PathVariable Integer reservationId,@RequestParam(name = "id", required = false) Integer userId) {
        User loggedInUser = userService.findOne(userId);

        if(loggedInUser!=null) {
            if (loggedInUser.getUserRole() != UserRole.USER) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
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


}
