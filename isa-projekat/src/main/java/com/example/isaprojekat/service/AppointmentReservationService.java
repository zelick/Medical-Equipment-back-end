package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.AppointmentReservationDTO;
import com.example.isaprojekat.dto.ItemDTO;
import com.example.isaprojekat.model.AppointmentReservation;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.repository.AppointmentReservationRepository;
import com.example.isaprojekat.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppointmentReservationService {
    @Autowired
    private AppointmentReservationRepository reservationRepository;

    public AppointmentReservation createReservation(AppointmentReservationDTO reservationDTO) {
        AppointmentReservation newReservation = new AppointmentReservation();
        newReservation.setAppointmentDate(reservationDTO.getAppointmentDate());
        newReservation.setAppointmentTime(reservationDTO.getAppointmentTime());
        newReservation.setAppointmentDuration(reservationDTO.getAppointmentDuration());
        newReservation.setUser(reservationDTO.getUser());
        newReservation.setItems(reservationDTO.getItems());
        return reservationRepository.save(newReservation);
    }

}
