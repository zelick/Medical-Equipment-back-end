package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.ReservationDTO;
import com.example.isaprojekat.enums.AppointmentStatus;
import com.example.isaprojekat.enums.ReservationStatus;
import com.example.isaprojekat.model.*;
import com.example.isaprojekat.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    private UserRepository userRepository;
    private AppointmentService appointmentService;
    private EquipmentService equipmentService;
    private CompanyAdminService companyAdminService;

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(ReservationDTO reservationDTO) {
        Reservation newReservation = new Reservation();
        newReservation.setStatus(ReservationStatus.PENDING);
        newReservation.setAppointment(reservationDTO.getAppointment());
        newReservation.setUser(reservationDTO.getUser());
        newReservation.setTotalPrice(reservationDTO.getTotalPrice());

        return reservationRepository.save(newReservation);
    }

    public Reservation setReservationPrice(Integer reservationId) {
        Reservation reservation = getReservationById(reservationId);
        double totalPrice = 0;
        for (Item i : reservation.getItems()) {
            totalPrice += i.getEquipment().getPrice() * i.getQuantity();
        }
        reservation.setTotalPrice(totalPrice);

        return reservationRepository.save(reservation);
    }

    public Reservation findOne(Integer id) {
        return reservationRepository.findById(id).orElseGet(null);
    }

    public void cancelReservation(ReservationDTO reservationDto) {
        Reservation reservation = reservationRepository.getById(reservationDto.getId());
        reservation.setStatus(ReservationStatus.CANCELED);
        reservationRepository.save(reservation);
        User user = reservation.getUser();
        int penaltyCount = calculatePenalty(reservation);
        user.setPenaltyPoints(user.getPenaltyPoints() + penaltyCount);
        Appointment appointment = appointmentService.findOne(reservation.getAppointment().getId());
        appointment.setStatus(AppointmentStatus.FREE);
        equipmentService.increaseEquimentMaxQuantity(reservation.getItems());
        userRepository.save(user);
    }

    private int calculatePenalty(Reservation reservation) {
        Date now = new Date();
        Date reservationStart = reservation.getAppointment().getAppointmentDate();
        long millisecondsUntilReservation = reservationStart.getTime() - now.getTime();
        long hoursUntilReservation = millisecondsUntilReservation / (60 * 60 * 1000);
        int penaltyCount = 1;
        if (hoursUntilReservation < 24) {
            penaltyCount = 2;
        }
        return penaltyCount;
    }

    public List<Reservation> GetAllNotCancelledReservationsForUser(User user) {
        List<Reservation> allByUser = reservationRepository.getAppointmentReservationsByUser(user);
        List<Reservation> notCancelledReservations = new ArrayList<>();
        for (Reservation r : allByUser) {
            if (r.getStatus().equals(ReservationStatus.PENDING)) {
                notCancelledReservations.add(r);
            }
        }
        return notCancelledReservations;
    }

    public Reservation getReservationById(int id) {
        return reservationRepository.findById(id);
    }

    public Reservation expireReservation(Reservation reservation) {
        reservation.setStatus(ReservationStatus.EXPIRED);
        reservation.getUser().setPenaltyPoints(2.0);
        equipmentService.increaseEquimentMaxQuantity(reservation.getItems());

        return reservationRepository.save(reservation);
    }

    public Reservation takeOverReservation(Reservation reservation) {
        reservation.setStatus(ReservationStatus.TAKEN_OVER);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> GetAllUsersReservations(User user) {
        return reservationRepository.getAppointmentReservationsByUser(user);
    }

    public List<Reservation> getAllTakenUsersReservations(User user) {
        List<Reservation> foundReservations = new ArrayList<>();
        List<Reservation> allUsersReservations = reservationRepository.getAppointmentReservationsByUser(user);

        for (Reservation r : allUsersReservations) {
            if (r.getStatus().equals(ReservationStatus.TAKEN_OVER)) {
                foundReservations.add(r);
            }
        }

        return foundReservations;
    }

    public List<Reservation> getAdminsAppointmentReservations (Integer adminId) {
        List<Reservation> reservations = reservationRepository.findAll();
        List <Appointment> companyAppointments = appointmentService.getCompanyAppointments(adminId);
        List<Reservation> foundReservations = new ArrayList<>();

        for (Reservation r : reservations) {
            for (Appointment a : companyAppointments) {
                if (a.getId().equals(r.getAppointment().getId())
                        && r.getStatus().equals(ReservationStatus.PENDING)) {
                    foundReservations.add(r);
                }
            }
        }

        return foundReservations;
    }
}
