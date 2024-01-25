package com.example.isaprojekat;

import com.example.isaprojekat.dto.ReservationDTO;
import com.example.isaprojekat.enums.AppointmentStatus;
import com.example.isaprojekat.model.Appointment;
import com.example.isaprojekat.model.Reservation;
import com.example.isaprojekat.service.AppointmentService;
import com.example.isaprojekat.service.ReservationService;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TxOptimisticApplicationTests {
    @Autowired
    private ReservationService reservationService;
    private AppointmentService appointmentService;

    /*@Before
    public void setUp() throws Exception {
        productService.save(new Product("P1", "O1", 5L));
        productService.save(new Product("P2","O2", 4L));
        productService.save(new Product("P3","O3", 3L));
        productService.save(new Product("P4","O4", 1L));
        productService.save(new Product("P5","O4", 1L));
    }*/

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testOptimisticLocking3() throws Throwable {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                //Appointment appointmentToUpdate = appointmentService.findOne(1);// ocitan objekat sa id 1
                //appointmentToUpdate.setStatus(AppointmentStatus.RESERVED);// izmenjen ucitan objekat

                try { Thread.sleep(3000); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
               // appointmentService.save(appointmentToUpdate);// bacice ObjectOptimisticLockingFailureException
            }
        });
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                Appointment appointmentToUpdate = appointmentService.findOne(1);// ocitan isti objekat sa id 1 kao i iz prvog threada
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