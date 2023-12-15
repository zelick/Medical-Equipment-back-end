package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.AppointmentReservationDTO;
import com.example.isaprojekat.dto.ItemDTO;
import com.example.isaprojekat.model.AppointmentReservation;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.service.AppointmentReservationService;
import com.example.isaprojekat.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/reservations")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentReservationController {
    @Autowired
    private AppointmentReservationService reservationService;

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

}
