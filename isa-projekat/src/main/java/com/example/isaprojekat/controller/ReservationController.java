package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.*;
import com.example.isaprojekat.model.*;
import com.example.isaprojekat.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "api/reservations")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    private UserService userService;
    private EmailService emailService;

    @PostMapping(value = "/create")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO, @RequestParam(name = "id", required = false) Integer userId) {

        if(!userService.isUser(userId)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            Reservation createdReservation = reservationService.createReservation(reservationDTO);
            return new ResponseEntity<>(new ReservationDTO(createdReservation), HttpStatus.CREATED);
        } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<Reservation> reservations = reservationService.findAll();

        List<ReservationDTO> reservationDTOS = new ArrayList<>();
        for (Reservation a : reservations) {
            reservationDTOS.add(new ReservationDTO(a));
        }

        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }

    @PutMapping(value = "/cancel")
    @CrossOrigin(origins = "http://localhost:4200")
    public String cancelReservation(@RequestBody ReservationDTO reservationDTO, @RequestParam(name = "id", required = false) Integer userId) {
        if(!userService.isUser(userId)){
            return "Unauthorized";
        }
        try {
            reservationService.cancelReservation(reservationDTO);
            return "Reservation canceled successfully.";
        } catch (Exception e) {
            return "Failed to cancel reservation.";
        }
    }

    @GetMapping(value = "/byUser/{username}")
    public ResponseEntity<List<ReservationDTO>> getByUser(@PathVariable String username, @RequestParam(name = "id", required = false) Integer userId) {

        User user = userService.findOneByEmail(username);
        User loggedInUser = userService.findOne(userId);

        if(!userService.isUser(userId)|| !Objects.equals(loggedInUser.getEmail(), user.getEmail())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Reservation> reservations = reservationService.GetAllNotCancelledReservationsForUser(user);
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for (Reservation r: reservations) {
            ReservationDTO reservationDTO = new ReservationDTO(r);
            reservationsDTO.add(reservationDTO);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllUsersReservations/{username}")
    public ResponseEntity<List<ReservationDTO>> getAllUsersReservations(@PathVariable String username, @RequestParam(name = "id", required = false) Integer userId) {

        User user = userService.findOneByEmail(username);
        User loggedInUser = userService.findOne(userId);
        if(!userService.isUser(userId)|| !Objects.equals(loggedInUser.getEmail(), user.getEmail())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Reservation> reservations = reservationService.GetAllUsersReservations(user);
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for (Reservation r: reservations) {
            ReservationDTO reservationDTO = new ReservationDTO(r);
            reservationsDTO.add(reservationDTO);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllTakenUsersReservations/{username}")
    public ResponseEntity<List<ReservationDTO>> getAllTakenUsersReservations(@PathVariable String username, @RequestParam(name = "id", required = false) Integer userId) {

        User user = userService.findOneByEmail(username);
        User loggedInUser = userService.findOne(userId);

        if(!userService.isUser(userId)|| !Objects.equals(loggedInUser.getEmail(), user.getEmail())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Reservation> reservations = reservationService.getAllTakenUsersReservations(user);
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for (Reservation r: reservations
        ) {
            ReservationDTO reservationDTO = new ReservationDTO(r);
            reservationsDTO.add(reservationDTO);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    @GetMapping(path = "findReservationById/{id}")
    public ReservationDTO findReservationById(@PathVariable int id) {
        Reservation foundReservation = reservationService.getReservationById(id);
        return new ReservationDTO(foundReservation);
    }

    @PutMapping(value = "/expired")
    @CrossOrigin(origins = "http://localhost:4200")
    public ReservationDTO expireReservation(@RequestBody ReservationDTO reservationDTO) {
        try {
            Reservation foundReservation = reservationService.getReservationById(reservationDTO.getId());
            Reservation expiredReservation = reservationService.expireReservation(foundReservation);
            return new ReservationDTO(expiredReservation);
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping(value = "/takeOver")
    @CrossOrigin(origins = "http://localhost:4200")
    public ReservationDTO takeOverReservation(@RequestBody ReservationDTO reservationDTO) {
        try {
            Reservation foundReservation = reservationService.getReservationById(reservationDTO.getId());
            Reservation takenOverReservation = reservationService.takeOverReservation(foundReservation);
            emailService.sendConfirmationReservationEmail(foundReservation);
            return new ReservationDTO(takenOverReservation);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/getAdminsAppointmentReservation/{admin_id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<ReservationDTO>> getAdminsAppointmentReservation(@PathVariable int admin_id){
        List<Reservation> reservations = reservationService.getAdminsAppointmentReservations(admin_id);
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for (Reservation r : reservations) {
            reservationsDTO.add(new ReservationDTO(r));
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }
}