package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.AppointmentReservation;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentReservationRepository extends JpaRepository<AppointmentReservation, Integer> {
    List<AppointmentReservation> getAppointmentReservationsByUser(User user);
    AppointmentReservation getById(Integer id);
}
