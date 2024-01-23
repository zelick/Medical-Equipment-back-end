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
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
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
    //@RequestParam(name = "id", required = false) Integer userId
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        /*User loggedInUser = userService.findOne(userId);

        if(loggedInUser!=null) {
            if (loggedInUser.getUserRole() != UserRole.USER) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
         */
        /*
        try {
            Reservation createdReservation = reservationService.createReservation(reservationDTO);
            return new ResponseEntity<>(new ReservationDTO(createdReservation), HttpStatus.CREATED);
        } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

         */
        Reservation createdReservation = reservationService.createReservation(reservationDTO);
        return new ResponseEntity<>(new ReservationDTO(createdReservation), HttpStatus.CREATED);
    }
    @GetMapping(value = "/all")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {

        List<Reservation> reservations = reservationService.findAll();

        // convert comapnies to DTOs
        List<ReservationDTO> reservationDTOS = new ArrayList<>();
        for (Reservation a : reservations) {
            reservationDTOS.add(new ReservationDTO(a));
        }

        return new ResponseEntity<>(reservationDTOS, HttpStatus.OK);
    }
    @PutMapping(value = "/cancel")
    @CrossOrigin(origins = "http://localhost:4200")
    public String cancelReservation(@RequestBody ReservationDTO reservationDTO) {
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

        if(loggedInUser!=null) {
            if (loggedInUser.getUserRole() != UserRole.USER || !Objects.equals(loggedInUser.getEmail(), user.getEmail())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        List<Reservation> reservations = reservationService.GetAllNotCancelledReservationsForUser(user);
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for (Reservation r: reservations
             ) {
            ReservationDTO reservationDTO = new ReservationDTO(r);
            reservationsDTO.add(reservationDTO);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllUsersReservations/{username}")
    public ResponseEntity<List<ReservationDTO>> getAllUsersReservations(@PathVariable String username, @RequestParam(name = "id", required = false) Integer userId) {

        User user = userService.findOneByEmail(username);
        User loggedInUser = userService.findOne(userId);

        if(loggedInUser!=null) {
            if (loggedInUser.getUserRole() != UserRole.USER || !Objects.equals(loggedInUser.getEmail(), user.getEmail())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        List<Reservation> reservations = reservationService.GetAllUsersReservations(user);
        List<ReservationDTO> reservationsDTO = new ArrayList<>();
        for (Reservation r: reservations
        ) {
            ReservationDTO reservationDTO = new ReservationDTO(r);
            reservationsDTO.add(reservationDTO);
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllTakenUsersReservations/{username}")
    public ResponseEntity<List<ReservationDTO>> getAllTakenUsersReservations(@PathVariable String username, @RequestParam(name = "id", required = false) Integer userId) {

        User user = userService.findOneByEmail(username);
        User loggedInUser = userService.findOne(userId);

        if(loggedInUser!=null) {
            if (loggedInUser.getUserRole() != UserRole.USER || !Objects.equals(loggedInUser.getEmail(), user.getEmail())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
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
    /*
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
     */
    /*
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
     */

    /*
    //ovde fali autorizacija - ispaviti
    @GetMapping(value = "/getAdminsAppointmentReservation/{admin_id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<List<ReservationDTO>> getAdminsAppointmentReservation(@PathVariable int admin_id){
        CompanyDTO companyDto = companyAdminService.getCompanyForAdmin(admin_id);
        Company company = companyService.findOne(companyDto.getId());
        List<ReservationDTO> reservationsDTO = new ArrayList<ReservationDTO>();
        Set<Integer> uniqueReservationIds = new HashSet<>();
        List<Item> allItems = itemService.findAll();
        for (Equipment e : company.getEquipments()) {
            for (Item i : allItems) {
                if (e.getId().equals(i.getEquipmentId())) {
                    int reservationId = i.getReservation();
                    // Check if the reservationId is unique
                    if (uniqueReservationIds.add(reservationId)) {
                        Reservation reservation = reservationService.getById(reservationId);
                        ReservationDTO dto = new ReservationDTO(reservation);
                        reservationsDTO.add(dto);
                    }
                }
            }
        }
        return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
    }

     */


}
