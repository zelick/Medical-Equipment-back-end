package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.Appointment;
import com.example.isaprojekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> getAppointmentReservationsByUser(User user);
    Appointment getById(Integer id);
}
