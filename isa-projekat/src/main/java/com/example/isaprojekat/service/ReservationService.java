package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.ReservationDTO;
import com.example.isaprojekat.enums.ReservationStatus;
import com.example.isaprojekat.model.*;
import com.example.isaprojekat.repository.ReservationRepository;
import com.example.isaprojekat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    public Reservation getById(Integer id){
        return reservationRepository.getById(id);
    }
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }
    public Reservation createReservation(ReservationDTO reservationDTO) {
        Reservation newReservation = new Reservation();
        newReservation.setStatus(ReservationStatus.PENDING);
        newReservation.setAppointment(reservationDTO.getAppointment());
        newReservation.setUser(reservationDTO.getUser());

        return reservationRepository.save(newReservation);
    }

    public void cancelReservation(ReservationDTO reservationDto){
        Reservation reservation = reservationRepository.getById(reservationDto.getId());
        reservation.setStatus(ReservationStatus.CANCELED);
        reservationRepository.save(reservation);

        User user = reservation.getUser();

        // Proveri vreme do početka termina
        Date now = new Date();
        Date reservationStart = reservation.getAppointment().getAppointmentDate();
        long millisecondsUntilReservation = reservationStart.getTime() - now.getTime();
        long hoursUntilReservation = millisecondsUntilReservation / (60 * 60 * 1000);

        // Dodaj penale prema pravilima
        int penaltyCount = 1;  // Podrazumevani broj penala
        if (hoursUntilReservation < 24) {
            penaltyCount = 2;  // Dodatni penal ako je otkazano unutar 24 sata
        }

        // Ažuriraj penale korisnika
        user.setPenaltyPoints(user.getPenaltyPoints()+penaltyCount);
        userRepository.save(user);

    }

    public List<Reservation> GetAllNotCancelledReservationsForUser(User user){
        List<Reservation> allByUser = reservationRepository.getAppointmentReservationsByUser(user);
        List<Reservation> notCancelledReservations = new ArrayList<>();
        for (Reservation r: allByUser
             ) {
            if(r.getStatus().equals(ReservationStatus.PENDING)){
                notCancelledReservations.add(r);
            }
        }
        return notCancelledReservations;
    }
    public Reservation getReservationById(int id){
        return reservationRepository.findById(id);
    }
    public Reservation expireReservation(Reservation reservation){
        reservation.setStatus(ReservationStatus.EXPIRED);
        reservation.getUser().setPenaltyPoints(2.0);
        return reservationRepository.save(reservation);
    }
    public Reservation takeOverReservation(Reservation reservation){
        for(Item item : reservation.getItems()){
            int itemQuantity = item.getQuantity();
            int itemMaxQuantity = item.getEquipment().getMaxQuantity();
            int newMaxQuantity = Math.max(itemMaxQuantity - itemQuantity, 0);
            item.getEquipment().setMaxQuantity(newMaxQuantity);
        }
        reservation.setStatus(ReservationStatus.TAKEN_OVER);
        return reservationRepository.save(reservation);

    public List<Reservation> GetAllUsersReservations(User user){
        return reservationRepository.getAppointmentReservationsByUser(user);
    }

    public List<Reservation> getAllTakenUsersReservations(User user){
        List<Reservation> foundReservations = new ArrayList<>();
        List<Reservation> allUsersReservations = reservationRepository.getAppointmentReservationsByUser(user);

        for (Reservation r : allUsersReservations) {
            if (r.getStatus().equals(ReservationStatus.TAKEN_OVER)){
                foundReservations.add(r);
            }
        }

        return foundReservations;

    }
}
