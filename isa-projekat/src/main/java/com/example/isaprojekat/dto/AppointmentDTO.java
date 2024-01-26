package com.example.isaprojekat.dto;

import com.example.isaprojekat.enums.AppointmentStatus;
import com.example.isaprojekat.model.Appointment;

import java.util.Date;

public class AppointmentDTO {
    private Integer id;
    private Integer adminId;
    private Date appointmentDate;
    private String appointmentTime;
    private Integer appointmentDuration;
    private AppointmentStatus status;
    public AppointmentDTO() {
    }
    public AppointmentDTO(Appointment appointment) {
        this(appointment.getId(), appointment.getAdminId(),
                appointment.getAppointmentDate(), appointment.getAppointmentTime(),
                appointment.getAppointmentDuration(), appointment.getStatus());
    }
    public AppointmentDTO(Integer id, Integer adminId,
                          Date appointmentDate,
                          String appointmentTime, Integer appointmentDuration, AppointmentStatus status) {
        this.id = id;
        this.adminId = adminId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentDuration = appointmentDuration;
        this.status = status;
    }


    public Integer getId() {
        return id;
    }
    public Integer getAdminId() {
        return adminId;
    }
    public Date getAppointmentDate() {
        return appointmentDate;
    }
    public String getAppointmentTime() {
        return appointmentTime;
    }
    public Integer getAppointmentDuration() {
        return appointmentDuration;
    }

    public AppointmentStatus getStatus() { return status;}
}
