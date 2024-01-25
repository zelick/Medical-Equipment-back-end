package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.AppointmentDTO;
import com.example.isaprojekat.enums.AppointmentStatus;
import com.example.isaprojekat.enums.ReservationStatus;
import com.example.isaprojekat.model.*;
import com.example.isaprojekat.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.lang.model.type.ArrayType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    private ReservationRepository reservationRepository;
    private CompanyAdminRepository companyAdminRepository;
    private UserRepository userRepository;
    private CompanyAdminService companyAdminService;

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
        newAppointment.setStatus(equipmentAppointmentDTO.getStatus());
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
        appointment.setStatus(appointmentDTO.getStatus());

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

    public List<Appointment> findCompanyAppointments(Integer companyId, Integer userId) {
        Date currentDate = new Date();
        List<Appointment> foundAppointments = new ArrayList<>();
        List<Appointment> appointments = appointmentRepository.findAll();
        List<Reservation> reservations = reservationRepository.findAll();

        for (Appointment a : appointments) {
            Company company = companyAdminService.getCompanyForAdmin(a.getAdminId());

            if (company != null && companyId.equals(company.getId())) {
                foundAppointments.add(a);
            }
        }

        List<Appointment> appointmentsToRemove = new ArrayList<>(foundAppointments);

        for (Reservation r : reservations) {
            for (Appointment a : foundAppointments) {
                if (a.getId().equals(r.getAppointment().getId()) && r.getStatus().equals(ReservationStatus.PENDING)) {
                    appointmentsToRemove.remove(a);
                }
                if (a.getId().equals(r.getAppointment().getId()) && r.getStatus().equals(ReservationStatus.CANCELED) && r.getUser().getId().equals(userId)){
                    appointmentsToRemove.remove(a);
                }
                if(a.getAppointmentDate().before(currentDate)){
                    appointmentsToRemove.remove(a);
                }

            }
        }

        return appointmentsToRemove;
    }

    public Appointment update(Appointment appointment)
    {
        appointment.setStatus(AppointmentStatus.RESERVED);
        return appointmentRepository.save(appointment);
    }
}
