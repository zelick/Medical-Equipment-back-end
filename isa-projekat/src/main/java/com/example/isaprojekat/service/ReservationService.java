package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.ReservationDTO;
import com.example.isaprojekat.model.*;
import com.example.isaprojekat.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    public Reservation getById(Integer id){
        return reservationRepository.getById(id);
    }
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }
    public Reservation createReservation(ReservationDTO reservationDTO) {
        Reservation newReservation = new Reservation();

        newReservation.setAppointment(reservationDTO.getAppointment());
        newReservation.setUser(reservationDTO.getUser());

        return reservationRepository.save(newReservation);
    }

    public List<Reservation> GetAllReservationsForUser(User user){
        return reservationRepository.getAppointmentReservationsByUser(user);
    }
    public Reservation getReservationById(int id){
        return reservationRepository.findById(id);
    }
}
