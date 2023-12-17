package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.EquipmentAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface EquipmentAppointmentRepository extends JpaRepository<EquipmentAppointment, Integer> {
    List<EquipmentAppointment> findEquipmentAppointmentByEquipmentId(Integer equipmentId);
    //List<EquipmentAppointment> findAllByAdmin_Id(int adminId);
}
