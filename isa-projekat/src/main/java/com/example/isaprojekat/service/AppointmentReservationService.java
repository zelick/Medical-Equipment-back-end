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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@AllArgsConstructor
public class AppointmentReservationService {
    @Autowired
    private AppointmentReservationRepository reservationRepository;
    @Autowired ItemService itemService;

    public AppointmentReservation createReservation(AppointmentReservationDTO reservationDTO) throws ParseException {

        AppointmentReservation newReservation = new AppointmentReservation();

        newReservation.setAppointmentDate(reservationDTO.getAppointmentDate());
        newReservation.setAppointmentTime(reservationDTO.getAppointmentTime());
        newReservation.setAppointmentDuration(reservationDTO.getAppointmentDuration());
        newReservation.setUser(reservationDTO.getUser());

        AppointmentReservation res = reservationRepository.save(newReservation);

        // Postavite Reservation za svaki Item
        for (Item i : reservationDTO.getItems()) {
            //i.setReservation(res);
            //itemService.save(i);
            newReservation.addItem(i);
        }
        for(Item i : newReservation.getItems()){
            i.setReservation(newReservation);
            itemService.save(i);
        }

        // Ažurirajte Items
        //res.setItems(reservationDTO.getItems());


        // Ponovo sačuvajte Reservation kako bi se ažurirala veza sa Items
        return reservationRepository.save(res);
    }


}
