package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.AppointmentReservation;
import com.example.isaprojekat.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentReservationRepository extends JpaRepository<AppointmentReservation, Integer> {

}
