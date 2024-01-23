package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.AppointmentDTO;
import com.example.isaprojekat.model.Appointment;
import com.example.isaprojekat.model.CompanyAdmin;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.model.Reservation;
import com.example.isaprojekat.repository.AppointmentRepository;
import com.example.isaprojekat.repository.CompanyAdminRepository;
import com.example.isaprojekat.repository.CompanyRepository;
import com.example.isaprojekat.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.type.ArrayType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    private ReservationRepository reservationRepository;
    private CompanyAdminRepository companyAdminRepository;

    public Appointment findOne(Integer id) {
        return appointmentRepository.findById(id).orElseGet(null);
    }
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }
    /*public List<EquipmentAppointment> findAllByAdminId(int admin_id){
        return equipmentAppointmentRepository.findAllByAdmin_Id(admin_id);
    }*/

    public void deleteById(Integer id){appointmentRepository.deleteById(id);}
    public Appointment createAppointment(AppointmentDTO equipmentAppointmentDTO) {

        Appointment newAppointment = new Appointment();
        newAppointment.setAppointmentDate(equipmentAppointmentDTO.getAppointmentDate());
        newAppointment.setAppointmentDuration(equipmentAppointmentDTO.getAppointmentDuration());
        newAppointment.setAppointmentTime(equipmentAppointmentDTO.getAppointmentTime());
        newAppointment.setAdminId(equipmentAppointmentDTO.getAdminId());
        System.out.println("Novi termin za preuzimanje:");
        System.out.println(newAppointment.getAppointmentTime());
        System.out.println(newAppointment.getAdminId());
        return appointmentRepository.save(newAppointment);
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

    public Appointment addAdminToAppointment(AppointmentDTO appointmentDTO, Integer companyId) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setAdminId(appointmentDTO.getAdminId());
        appointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
        appointment.setAppointmentDuration(appointmentDTO.getAppointmentDuration());

        if (findAvailableAdmin(appointment, companyId).equals(-1)) {
            return appointment;
        }

        appointment.setAdminId(findAvailableAdmin(appointment, companyId));

        return appointmentRepository.save(appointment);
    }

    private Integer findAvailableAdmin(Appointment appointment, Integer companyId) {
        List<Reservation> reservations = reservationRepository.findAll();
        List<CompanyAdmin> allCompanyAdmins = companyAdminRepository.findAll();
        List<Integer> companyAdmins = new ArrayList<>();
        List<Integer> unavailableInDates = new ArrayList<>();
        List<Integer> unavailableInTimes = new ArrayList<>();
        List<Integer> companyAdminsCopy = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


        for (CompanyAdmin ca : allCompanyAdmins) {
            if (ca.getCompany_id().equals(companyId)) {
                companyAdmins.add(ca.getUser_id());
                companyAdminsCopy.add(ca.getUser_id());
            }
        }
        String appointmentDateStr = dateFormat.format(appointment.getAppointmentDate());
        for (Reservation r : reservations) {
            String reservationDateStr = dateFormat.format(r.getAppointment().getAppointmentDate());
            if (appointmentDateStr.equals(reservationDateStr)) {
                unavailableInDates.add(r.getAppointment().getAdminId());
            }
        }

        if (!unavailableInDates.isEmpty()) {
            companyAdmins.removeAll(unavailableInDates);
        }

        if (companyAdmins.isEmpty()) {
            for (Reservation r : reservations) {
                int existingAppointmentTime = convertTimeToMinutes(r.getAppointment().getAppointmentTime());
                int existingAppointmentDuration = r.getAppointment().getAppointmentDuration();
                int newAppointmentTime = convertTimeToMinutes(appointment.getAppointmentTime());
                if (existingAppointmentTime <= newAppointmentTime + appointment.getAppointmentDuration() &&
                        newAppointmentTime <= existingAppointmentTime + existingAppointmentDuration) {
                    unavailableInTimes.add(r.getAppointment().getAdminId());
                }
            }
        }

        companyAdmins.addAll(companyAdminsCopy);

        if (!unavailableInTimes.isEmpty()) {
            companyAdmins.removeAll(unavailableInTimes);
        }

        if (companyAdmins.isEmpty()) {
            return -1;
        }

        return companyAdmins.get(0);
    }

    int convertTimeToMinutes(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }
}
