package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.CompanyDTO;
import com.example.isaprojekat.dto.EquipmentAppointmentDTO;
import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.EquipmentAppointment;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.repository.CompanyRepository;
import com.example.isaprojekat.repository.EquipmentAppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.security.sasl.SaslServer;
import java.util.ArrayList;
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
    public List<EquipmentAppointment> findAvailableAppointments(List<Item> items) {
        List<EquipmentAppointment> commonAvailableAppointments = new ArrayList<>();

        // Find available appointments for the first item
        List<EquipmentAppointment> availableAppointments = findAvailableAppointmentsForItem(items.get(0));

        // Check for common available appointments among all items
        for (EquipmentAppointment appointment : availableAppointments) {
            if (isCommonAvailableAppointment(appointment, items)) {
                commonAvailableAppointments.add(appointment);
            }
        }

        return commonAvailableAppointments;
    }
    private List<EquipmentAppointment> findAvailableAppointmentsForItem(Item item) {
        // Implement logic to find available appointments for a given item
        // You can use the equipmentId and other criteria to query the database

        // For demonstration purposes, let's assume a method findAvailableAppointmentsByEquipmentId
        return equipmentAppointmentRepository.findEquipmentAppointmentByEquipmentId(item.getEquipmentId());
    }
    private boolean isCommonAvailableAppointment(EquipmentAppointment appointment, List<Item> items) {
        // Check if the appointment time is available for all items
        for (Item item : items) {
            List<EquipmentAppointment> availableAppointments = findAvailableAppointmentsForItem(item);
            if (!isAppointmentAvailable(appointment, availableAppointments)) {
                return false;
            }
        }
        return true;
    }
    private boolean isAppointmentAvailable(EquipmentAppointment appointment, List<EquipmentAppointment> availableAppointments) {
        // Check if the appointment time is available for a specific item
        for (EquipmentAppointment availableAppointment : availableAppointments) {
            if (isTimeSlotOverlap(appointment, availableAppointment)) {
                return true;
            }
        }
        return false;
    }
    private boolean isTimeSlotOverlap(EquipmentAppointment appointment1, EquipmentAppointment appointment2) {
        // Implement logic to check if two appointments have a time slot overlap
        // You can compare appointment start and end times to determine overlap

        // For demonstration purposes, let's assume a method isTimeSlotOverlap
        return appointment1.getAppointmentDate().equals(appointment2.getAppointmentDate())
                && appointment1.getAppointmentTime().equals(appointment2.getAppointmentTime());
    }
}
