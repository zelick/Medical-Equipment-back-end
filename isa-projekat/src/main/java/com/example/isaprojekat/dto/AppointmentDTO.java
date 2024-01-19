package com.example.isaprojekat.dto;

import com.example.isaprojekat.model.Appointment;
import com.example.isaprojekat.model.User;

import java.time.LocalDateTime;

public class AppointmentDTO {
    private Integer id;
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime appointmentDate;
    private String appointmentTime;
    private Integer appointmentDuration;
    private User user;

    public AppointmentDTO() {

    }

    public AppointmentDTO(Appointment reservation) {
        this.id =reservation.getId();
        this.appointmentDate = reservation.getAppointmentDate();
        this.appointmentTime = reservation.getAppointmentTime();
        this.appointmentDuration = reservation.getAppointmentDuration();
        this.user = reservation.getUser();
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




}
