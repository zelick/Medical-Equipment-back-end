package com.example.isaprojekat.dto;

import com.example.isaprojekat.enums.AppointmentStatus;
import com.example.isaprojekat.model.EquipmentAppointment;

import java.util.Date;

public class EquipmentAppointmentDTO {
    private Integer id;
    private Integer equipmentId;
    private Integer adminId;
    private String adminName;
    private String adminSurname;
    private AppointmentStatus status;
    private Date appointmentDate;
    private String appointmentTime;
    private Integer appointmentDuration;
    public EquipmentAppointmentDTO() {
    }
    public EquipmentAppointmentDTO(EquipmentAppointment equipmentAppointment) {
        this(equipmentAppointment.getId(), equipmentAppointment.getEquipmentId(),
                equipmentAppointment.getAdminId(), equipmentAppointment.getAdminName(),
                equipmentAppointment.getAdminSurname(), equipmentAppointment.getStatus(),
                equipmentAppointment.getAppointmentDate(), equipmentAppointment.getAppointmentTime(),
                equipmentAppointment.getAppointmentDuration());
    }
    public EquipmentAppointmentDTO(Integer id, Integer equipmentId, Integer adminId, String adminName,
                                   String adminSurname, AppointmentStatus status, Date appointmentDate,
                                   String appointmentTime, Integer appointmentDuration) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminSurname = adminSurname;
        this.status = status;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentDuration = appointmentDuration;
    }

    public Integer getId() {
        return id;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }


    public Integer getAdminId() {
        return adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getAdminSurname() {
        return adminSurname;
    }


    public AppointmentStatus getStatus() {
        return status;
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
}
