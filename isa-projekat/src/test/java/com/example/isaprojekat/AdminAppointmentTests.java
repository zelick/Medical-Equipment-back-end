package com.example.isaprojekat;

import com.example.isaprojekat.enums.AppointmentStatus;
import com.example.isaprojekat.model.Appointment;
import com.example.isaprojekat.repository.AppointmentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.dao.PessimisticLockingFailureException;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminAppointmentTests {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test(expected = PessimisticLockingFailureException.class)
    @Transactional
    @Rollback(false)
    public void testPessimisticLockingScenario() throws Throwable {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                Appointment newAppointment = new Appointment();
                newAppointment.setAppointmentDate(new Date());
                newAppointment.setAppointmentDuration(20);
                newAppointment.setAppointmentTime("12:00");
                newAppointment.setAdminId(3);
                newAppointment.setStatus(AppointmentStatus.FREE);
                appointmentRepository.save(newAppointment); // izvrsavanje transakcione metode traje oko 200 milisekundi
            }
        });
        Future<?> future2 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                Appointment newAppointment = new Appointment();
                newAppointment.setAppointmentDate(new Date());
                newAppointment.setAppointmentDuration(20);
                newAppointment.setAppointmentTime("12:00");
                newAppointment.setAdminId(3);
                newAppointment.setStatus(AppointmentStatus.FREE);
                try { Thread.sleep(150); } catch (InterruptedException e) { }// otprilike 150 milisekundi posle prvog threada krece da se izvrsava drugi
                /*
                 * Drugi thread pokusava da izvrsi transakcionu metodu findOneById dok se prvo izvrsavanje iz prvog threada jos nije zavrsilo.
                 * Metoda je oznacena sa NO_WAIT, sto znaci da drugi thread nece cekati da prvi thread zavrsi sa izvrsavanjem metode vec ce odmah dobiti PessimisticLockingFailureException uz poruke u logu:
                 * [pool-1-thread-2] o.h.engine.jdbc.spi.SqlExceptionHelper : SQL Error: 0, SQLState: 55P03
                 * [pool-1-thread-2] o.h.engine.jdbc.spi.SqlExceptionHelper : ERROR: could not obtain lock on row in relation "product"
                 * Prema Postgres dokumentaciji https://www.postgresql.org/docs/9.3/errcodes-appendix.html, kod 55P03 oznacava lock_not_available
                 */
                appointmentRepository.save(newAppointment);
            }
        });
        try {
            future2.get(); // podize ExecutionException za bilo koji izuzetak iz drugog child threada
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas PessimisticLockingFailureException
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

}
