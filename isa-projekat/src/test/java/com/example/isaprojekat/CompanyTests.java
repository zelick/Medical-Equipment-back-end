package com.example.isaprojekat;

import com.example.isaprojekat.dto.AppointmentDTO;
import com.example.isaprojekat.enums.AppointmentStatus;
import com.example.isaprojekat.model.Appointment;
import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.Equipment;
import com.example.isaprojekat.repository.AppointmentRepository;
import com.example.isaprojekat.service.AppointmentService;
import com.example.isaprojekat.service.CompanyService;
import com.example.isaprojekat.service.EquipmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.dao.PessimisticLockingFailureException;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyTests {

    @Autowired
    private CompanyService companyService;

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testOptimisticLockingCompany() throws Throwable { //3

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                Company companyToUpdate = companyService.findOne(1);// ocitan objekat sa id 1
                companyToUpdate.setName("NewName1");// izmenjen ucitan objekat

                try { Thread.sleep(3000); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
                companyService.save(companyToUpdate);// bacice ObjectOptimisticLockingFailureException
            }
        });
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                Company companyToUpdate = companyService.findOne(1);// ocitan objekat sa id 1
                companyToUpdate.setName("NewName2");// izmenjen ucitan objekat
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
                companyService.save(companyToUpdate);
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
