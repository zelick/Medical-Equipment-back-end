package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.AppointmentDTO;
import com.example.isaprojekat.enums.AppointmentStatus;
import com.example.isaprojekat.enums.ReservationStatus;
import com.example.isaprojekat.model.*;
import com.example.isaprojekat.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.lang.model.type.ArrayType;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    private ReservationRepository reservationRepository;
    private CompanyAdminRepository companyAdminRepository;
    private CompanyAdminService companyAdminService;

    public Appointment findOne(Integer id) {
        return appointmentRepository.findById(id).orElseGet(null);
    }
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    public void deleteById(Integer id){appointmentRepository.deleteById(id);}

    @Transactional
    public Appointment createAppointment(AppointmentDTO equipmentAppointmentDTO) {

        Appointment newAppointment = new Appointment();
        newAppointment.setAppointmentDate(equipmentAppointmentDTO.getAppointmentDate());
        newAppointment.setAppointmentDuration(equipmentAppointmentDTO.getAppointmentDuration());
        newAppointment.setAppointmentTime(equipmentAppointmentDTO.getAppointmentTime());
        newAppointment.setAdminId(equipmentAppointmentDTO.getAdminId());
        newAppointment.setStatus(equipmentAppointmentDTO.getStatus());
        return appointmentRepository.save(newAppointment);
    }



    private boolean isAppointmentAvailable(Appointment appointment, List<Appointment> availableAppointments) {
        for (Appointment availableAppointment : availableAppointments) {
            if (isTimeSlotOverlap(appointment, availableAppointment)) {
                return true;
            }
        }
        return false;
    }
    private boolean isTimeSlotOverlap(Appointment appointment1, Appointment appointment2) {
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
        List<Appointment> appointments = appointmentRepository.findAll();
        List<Reservation> reservations = reservationRepository.findAll();
        List<Appointment> filteredAppointments = filterAppointments(appointments, companyId);
        removeAppointmentsWithReservations(filteredAppointments, reservations, userId);
        removeAppointmentsWithPastDates(filteredAppointments, currentDate);

        return filteredAppointments;
    }

    private List<Appointment> filterAppointments(List<Appointment> appointments, Integer companyId) {
        return appointments.stream()
                .filter(a -> {
                    Company company = companyAdminService.getCompanyForAdmin(a.getAdminId());
                    return company != null && companyId.equals(company.getId()) && a.getStatus() == AppointmentStatus.FREE;
                })
                .collect(Collectors.toList());
    }

    private void removeAppointmentsWithReservations(List<Appointment> appointments, List<Reservation> reservations, Integer userId) {
        appointments.removeIf(a ->
                reservations.stream()
                        .anyMatch(r ->
                                a.getId().equals(r.getAppointment().getId()) &&
                                        (r.getStatus().equals(ReservationStatus.PENDING) ||
                                                (r.getStatus().equals(ReservationStatus.CANCELED) && r.getUser().getId().equals(userId))))
        );
    }

    private void removeAppointmentsWithPastDates(List<Appointment> appointments, Date currentDate) {
        appointments.removeIf(a -> a.getAppointmentDate().before(currentDate));
    }
    public Appointment update(Appointment appointment)
    {
        appointment.setStatus(AppointmentStatus.RESERVED);
        return appointmentRepository.save(appointment);
    }

    public Appointment save(Appointment appointment)
    {
        return appointmentRepository.save(appointment);
    }
}
