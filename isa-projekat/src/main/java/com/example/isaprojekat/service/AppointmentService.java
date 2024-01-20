package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.AppointmentDTO;
import com.example.isaprojekat.model.Appointment;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.repository.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService {
    @Autowired
    private AppointmentRepository equipmentAppointmentRepository;

    public Appointment findOne(Integer id) {
        return equipmentAppointmentRepository.findById(id).orElseGet(null);
    }
    public List<Appointment> findAll() {
        return equipmentAppointmentRepository.findAll();
    }
    /*public List<EquipmentAppointment> findAllByAdminId(int admin_id){
        return equipmentAppointmentRepository.findAllByAdmin_Id(admin_id);
    }*/

    public void deleteById(Integer id){equipmentAppointmentRepository.deleteById(id);}
    public Appointment createAppointment(AppointmentDTO equipmentAppointmentDTO) {

        Appointment newAppointment = new Appointment();
        newAppointment.setAppointmentDate(equipmentAppointmentDTO.getAppointmentDate());
        newAppointment.setAppointmentDuration(equipmentAppointmentDTO.getAppointmentDuration());
        newAppointment.setAppointmentTime(equipmentAppointmentDTO.getAppointmentTime());
        newAppointment.setAdminId(equipmentAppointmentDTO.getAdminId());
        System.out.println("Novi termin za preuzimanje:");
        System.out.println(newAppointment.getAppointmentTime());
        System.out.println(newAppointment.getAdminId());
        return equipmentAppointmentRepository.save(newAppointment);
    }
    /*
    public List<Appointment> findAvailableAppointments(List<Item> items) {
        List<Appointment> commonAvailableAppointments = new ArrayList<>();

        // Find available appointments for the first item
        List<Appointment> availableAppointments = findAvailableAppointmentsForItem(items.get(0));

        // Check for common available appointments among all items
        for (Appointment appointment : availableAppointments) {
            if (isCommonAvailableAppointment(appointment, items)) {
                commonAvailableAppointments.add(appointment);
            }
        }

        return commonAvailableAppointments;
    }
    */

    /*
    private List<Appointment> findAvailableAppointmentsForItem(Item item) {
        // Implement logic to find available appointments for a given item
        // You can use the equipmentId and other criteria to query the database

        // For demonstration purposes, let's assume a method findAvailableAppointmentsByEquipmentId
        //return equipmentAppointmentRepository.findEquipmentAppointmentByEquipmentId(item.getEquipmentId());
    }
    */

    /*
    private boolean isCommonAvailableAppointment(Appointment appointment, List<Item> items) {
        // Check if the appointment time is available for all items
        for (Item item : items) {
            List<Appointment> availableAppointments = findAvailableAppointmentsForItem(item);
            if (!isAppointmentAvailable(appointment, availableAppointments)) {
                return false;
            }
        }
        return true;
    }
    */
    private boolean isAppointmentAvailable(Appointment appointment, List<Appointment> availableAppointments) {
        // Check if the appointment time is available for a specific item
        for (Appointment availableAppointment : availableAppointments) {
            if (isTimeSlotOverlap(appointment, availableAppointment)) {
                return true;
            }
        }
        return false;
    }
    private boolean isTimeSlotOverlap(Appointment appointment1, Appointment appointment2) {
        // Implement logic to check if two appointments have a time slot overlap
        // You can compare appointment start and end times to determine overlap

        // For demonstration purposes, let's assume a method isTimeSlotOverlap
        return appointment1.getAppointmentDate().equals(appointment2.getAppointmentDate())
                && appointment1.getAppointmentTime().equals(appointment2.getAppointmentTime());
    }
}
