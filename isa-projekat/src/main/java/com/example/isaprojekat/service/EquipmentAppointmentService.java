package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.CompanyDTO;
import com.example.isaprojekat.dto.EquipmentAppointmentDTO;
import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.EquipmentAppointment;
import com.example.isaprojekat.repository.CompanyRepository;
import com.example.isaprojekat.repository.EquipmentAppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.security.sasl.SaslServer;
import java.util.List;

@Service
@AllArgsConstructor
public class EquipmentAppointmentService {
    @Autowired
    private EquipmentAppointmentRepository equipmentAppointmentRepository;

    public EquipmentAppointment findOne(Integer id) {
        return equipmentAppointmentRepository.findById(id).orElseGet(null);
    }
    public List<EquipmentAppointment> findAll() {
        return equipmentAppointmentRepository.findAll();
    }

    public EquipmentAppointment createAppointment(EquipmentAppointmentDTO equipmentAppointmentDTO) {

        EquipmentAppointment newAppointment = new EquipmentAppointment();
        newAppointment.setAppointmentDate(equipmentAppointmentDTO.getAppointmentDate());
        newAppointment.setAppointmentDuration(equipmentAppointmentDTO.getAppointmentDuration());
        newAppointment.setAppointmentTime(equipmentAppointmentDTO.getAppointmentTime());
        newAppointment.setEquipmentId(equipmentAppointmentDTO.getEquipmentId());
        newAppointment.setAdminName(equipmentAppointmentDTO.getAdminName());
        newAppointment.setAdminSurname(equipmentAppointmentDTO.getAdminSurname());
        newAppointment.setAdminId(equipmentAppointmentDTO.getAdminId());
        System.out.println("Novi termin za preuzimanje:");
        System.out.println(newAppointment.getAppointmentTime());
        System.out.println(newAppointment.getEquipmentId());
        System.out.println(newAppointment.getAdminId());
        System.out.println(newAppointment.getAdminSurname());
        System.out.println(newAppointment.getAdminName());
        return equipmentAppointmentRepository.save(newAppointment);
    }
}
