package com.example.isaprojekat.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "adminId", nullable = false)
    private Integer adminId;
    @Column(name = "appointmentDate", nullable = false)
    private Date appointmentDate;
    @Column(name = "appointmentTime", nullable = false)
    private String appointmentTime;
    @Column(name = "appointmentDuration", nullable = false)
    private Integer appointmentDuration;
    public Appointment() {
    }
    public Appointment(Integer id, Integer adminId, Date appointmentDate,
                       String appointmentTime, Integer appointmentDuration) {
        this.id = id;
        this.adminId = adminId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentDuration = appointmentDuration;
    }

    // Getteri i setteri za sva polja
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Integer getAppointmentDuration() {
        return appointmentDuration;
    }

    public void setAppointmentDuration(Integer appointmentDuration) {
        this.appointmentDuration = appointmentDuration;
    }
}
