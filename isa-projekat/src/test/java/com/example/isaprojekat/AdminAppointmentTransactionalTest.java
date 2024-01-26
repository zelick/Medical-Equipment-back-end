/*package com.example.isaprojekat;

import com.example.isaprojekat.dto.ReservationDTO;
import com.example.isaprojekat.model.Appointment;
import com.example.isaprojekat.model.User;
import com.example.isaprojekat.service.AppointmentService;
import com.example.isaprojekat.service.ReservationService;
import com.example.isaprojekat.service.UserService;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminAppointmentTransactionalTest {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;


    //jedan administrator kompanije ne može istovremeno da bude prisutan na
    //više različitih termina preuzimanja
    //Kada se desi konflikt:
    /*Appointmetn je vezan za admina*/
    /*Ovo se moze desiti prilikom kreiranja rezervacije -  Appointment se postavlja na RESERVED*/
    /*Ako drugi korisink pokusa da kreira rezervaciju za isti taj Appotintment - KONFLIKT*/
/*
    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testConcurrentReservationsForSameAppointment() throws Throwable {
        //Simulacija istog termina preuzimanja (appointment) za oba korisnika
        Appointment appointment = appointmentService.findOne(2); //status free, admin ISTI

        ExecutorService executor = Executors.newFixedThreadPool(2); //dva thread-a
        Future<?> future1 = executor.submit(() -> {
            // Prvi korisnik pokušava da napravi rezervaciju
            ReservationDTO reservationDTO = new ReservationDTO();
            User user1 =  userService.findOne(1);
            reservationDTO.setAppointment(appointment);
            reservationDTO.setUser(user1);
            // Kada pokušamo da sačuvamo rezervaciju, servis treba da baci ObjectOptimisticLockingFailureException
            reservationService.createReservation(reservationDTO);
            //treba proveriti da li se status Appotinemtn promenio na RESERVED
        });

        // Drugi korisnik takođe pokušava da napravi rezervaciju za isti termin
        executor.submit(() -> {
            ReservationDTO reservationDTO2 = new ReservationDTO();
            reservationDTO2.setAppointment(appointment);
            User user2 =  userService.findOne(2);
            reservationDTO2.setUser(user2);
            // Očekujem da rezervacija neće biti sačuvana zbog konflikta s prvim korisnikom
            reservationService.createReservation(reservationDTO2);
        });

        try {
            future1.get(); // Očekujemo da prvi korisnik dobije izuzetak
        } catch (Exception e) {
            // Proveravamo da li je izuzetak prouzrokovan ObjectOptimisticLockingFailureException
            assertTrue(e.getCause() instanceof ObjectOptimisticLockingFailureException);
            throw e.getCause();
        } finally {
            // Zatvaramo executor
            executor.shutdown();
        }
    }
}
*/
