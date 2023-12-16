package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.AppointmentReservationDTO;
import com.example.isaprojekat.dto.ItemDTO;
import com.example.isaprojekat.dto.UserDTO;
import com.example.isaprojekat.model.AppointmentReservation;
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

@RestController
@RequestMapping(value = "api/reservations")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentReservationController {
    @Autowired
    private AppointmentReservationService reservationService;
    @Autowired
    private UserService userService;

    @PostMapping(value = "/create")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<AppointmentReservationDTO> createReservation(@RequestBody AppointmentReservationDTO reservationDTO) {
        try {
            AppointmentReservation createdReservation = reservationService.createReservation(reservationDTO);
            return new ResponseEntity<>(new AppointmentReservationDTO(createdReservation), HttpStatus.CREATED);
        } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/byUser/{username}")
    public ResponseEntity<List<AppointmentReservationDTO>> getByUser(@PathVariable String username) {

        User user = userService.findOneByEmail(username);
        List<AppointmentReservation> reservations = reservationService.GetAllReservationsForUser(user);
        List<AppointmentReservationDTO> reservationsDTO = new ArrayList<>();
        for (AppointmentReservation r: reservations
             ) {
            AppointmentReservationDTO reservationDTO = new AppointmentReservationDTO(r);
            reservationsDTO.add(reservationDTO);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

}
