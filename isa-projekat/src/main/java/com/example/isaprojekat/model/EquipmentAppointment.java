package com.example.isaprojekat.model;

import com.example.isaprojekat.enums.AppointmentStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
public class EquipmentAppointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "equipmentId", nullable = false)
    private Integer equipmentId;
    @Column(name = "adminId", nullable = false)
    private Integer adminId;
    @Column(name = "adminName", nullable = false)
    private String adminName;
    @Column(name = "adminSurname", nullable = false)
    private String adminSurname;
    @Column(name = "status", nullable = false)
    private AppointmentStatus status;
    @Column(name = "appointmentDate", nullable = false)
    private Date appointmentDate;
    @Column(name = "appointmentTime", nullable = false)
    private String appointmentTime;
    @Column(name = "appointmentDuration", nullable = false)
    private Integer appointmentDuration;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "userId", nullable = false)
    private Integer userId;
    @Column(name = "qrCodeSent", nullable = false)
    private Boolean qrCodeSent;
    public EquipmentAppointment() {
    }

    // Konstruktor sa svim poljima
    public EquipmentAppointment(Integer id, Integer equipmentId, Integer adminId, String adminName,
                                String adminSurname, AppointmentStatus status, Date appointmentDate,
                                String appointmentTime, Integer appointmentDuration, Integer quantity,
                                Integer userId, Boolean qrCodeSent) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminSurname = adminSurname;
        this.status = status;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentDuration = appointmentDuration;
        this.quantity = quantity;
        this.userId = userId;
        this.qrCodeSent = qrCodeSent;
    }

    // Getteri i setteri za sva polja
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
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

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
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
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Boolean getQrCodeSent() {
        return qrCodeSent;
    }

    public void setQrCodeSent(Boolean qrCodeSent) {
        this.qrCodeSent = qrCodeSent;
    }
}
