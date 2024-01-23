package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.Reservation;
import com.example.isaprojekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> getAppointmentReservationsByUser(User user);
    Reservation findById(int id);
}
