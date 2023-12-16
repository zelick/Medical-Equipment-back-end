package com.example.isaprojekat.dto;

import com.example.isaprojekat.model.AppointmentReservation;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AppointmentReservationDTO {
    private Integer id;
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public void setAppointmentDuration(Integer appointmentDuration) {
        this.appointmentDuration = appointmentDuration;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


}
