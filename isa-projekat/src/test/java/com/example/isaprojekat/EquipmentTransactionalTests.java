package com.example.isaprojekat;

import com.example.isaprojekat.dto.ReservationDTO;
import com.example.isaprojekat.enums.AppointmentStatus;
import com.example.isaprojekat.model.Appointment;
import com.example.isaprojekat.model.Equipment;
import com.example.isaprojekat.model.Reservation;
import com.example.isaprojekat.service.AppointmentService;
import com.example.isaprojekat.service.EquipmentService;
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
public class EquipmentTransactionalTests {
    @Autowired
    private EquipmentService equipmentService;

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testOptimisticLockingReservingUnavailableEquipment() throws Throwable { //3

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                Equipment equipmentToUpdate = equipmentService.findOne(1);// ocitan objekat sa id 1
                equipmentToUpdate.setMaxQuantity(3);// izmenjen ucitan objekat

                try { Thread.sleep(3000); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
                equipmentService.save(equipmentToUpdate);// bacice ObjectOptimisticLockingFailureException
            }
        });
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                Equipment equipmentToUpdate = equipmentService.findOne(1);// ocitan objekat sa id 1
                equipmentToUpdate.setMaxQuantity(0); // izmenjen ucitan objekat
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
                equipmentService.save(equipmentToUpdate);            }
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