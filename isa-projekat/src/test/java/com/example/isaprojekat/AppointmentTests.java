package com.example.isaprojekat;

import com.example.isaprojekat.enums.AppointmentStatus;
import com.example.isaprojekat.model.Appointment;
import com.example.isaprojekat.service.AppointmentService;
import com.example.isaprojekat.service.ReservationService;
import com.example.isaprojekat.service.UserService;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentTests {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private UserService userService;


    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testOptimisticLockingAppointmentUnavaiable() throws Throwable { //3

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                Appointment appointmentToUpdate = appointmentService.findOne(14);// ocitan objekat sa id 1
                appointmentToUpdate.setStatus(AppointmentStatus.RESERVED);// izmenjen ucitan objekat

                try { Thread.sleep(3000); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
                appointmentService.save(appointmentToUpdate);// bacice ObjectOptimisticLockingFailureException
            }
        });
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                Appointment appointmentToUpdate = appointmentService.findOne(14);// ocitan isti objekat sa id 1 kao i iz prvog threada
                appointmentToUpdate.setStatus(AppointmentStatus.RESERVED);// izmenjen ucitan objekat
                /*
                 * prvi ce izvrsiti izmenu i izvrsi upit:
                 * Hibernate:
                 *     update
                 *         product
                 *     set
                 *         name=?,
                 *         origin=?,
                 *         price=?,
                 *         version=?
                 *     where
                 *         id=?
                 *         and version=?
                 *
                 * Moze se primetiti da automatski dodaje na upit i proveru o verziji
                 */
                appointmentService.save(appointmentToUpdate);
           }
        });
        try {
            future1.get(); // podize ExecutionException za bilo koji izuzetak iz prvog child threada
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }
}