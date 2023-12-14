package com.example.isaprojekat.model;

import com.example.isaprojekat.enums.AppointmentStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class EquipmentAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "adminId", nullable = false)
    private Integer adminId;
    @Column(name = "equipmentId", nullable = false)
    private Integer equipmentId;
    @Column(name = "adminName", nullable = false)
    private String adminName;
    @Column(name = "adminSurname", nullable = false)
    private String adminSurname;
    @Column(name = "appointmentDate", nullable = false)
    private Date appointmentDate;
    @Column(name = "appointmentTime", nullable = false)
    private String appointmentTime;
    @Column(name = "appointmentDuration", nullable = false)
    private Integer appointmentDuration;
    public EquipmentAppointment() {
    }
    public EquipmentAppointment(Integer id, Integer adminId, Integer equipmentId,
                                String adminName,
                                String adminSurname, Date appointmentDate,
                                String appointmentTime, Integer appointmentDuration) {
        this.id = id;
        this.adminId = adminId;
        this.equipmentId = equipmentId;
        this.adminName = adminName;
        this.adminSurname = adminSurname;
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

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminSurname() {
        return adminSurname;
    }

    public void setAdminSurname(String adminSurname) {
        this.adminSurname = adminSurname;
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

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }
}
