package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    //List<Appointment> findEquipmentAppointmentByEquipmentId(Integer equipmentId);
    //List<EquipmentAppointment> findAllByAdmin_Id(int adminId);
}
