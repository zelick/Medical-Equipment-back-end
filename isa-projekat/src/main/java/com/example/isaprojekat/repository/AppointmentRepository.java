package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.Appointment;
import com.example.isaprojekat.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
//@Transactional(readOnly = true)
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    //List<Appointment> findEquipmentAppointmentByEquipmentId(Integer equipmentId);
    //List<EquipmentAppointment> findAllByAdmin_Id(int adminId);
    //@Transactional(readOnly = false)
    Appointment save(Appointment appointment);

    @Query("SELECT a FROM Appointment a WHERE a.id = :id")
    Appointment findApById(@Param("id") Integer id);
}
