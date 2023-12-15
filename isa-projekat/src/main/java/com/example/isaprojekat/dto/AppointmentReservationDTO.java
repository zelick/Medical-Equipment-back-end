package com.example.isaprojekat.dto;

import com.example.isaprojekat.model.AppointmentReservation;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentReservationDTO {
    private Integer id;
    private LocalDateTime appointmentDate;
    private String appointmentTime;
    private Integer appointmentDuration;
    private User user;
    private List<Item> items;

    public AppointmentReservationDTO() {
        this.items = new ArrayList<>();
    }

    public AppointmentReservationDTO(AppointmentReservation reservation) {
        this.appointmentDate = reservation.getAppointmentDate();
        this.appointmentTime = reservation.getAppointmentTime();
        this.appointmentDuration = reservation.getAppointmentDuration();
        this.user = reservation.getUser();
        this.items = reservation.getItems();
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public Integer getAppointmentDuration() {
        return appointmentDuration;
    }

    public User getUser() {
        return user;
    }

    public List<Item> getItems() {
        return items;
    }
}
