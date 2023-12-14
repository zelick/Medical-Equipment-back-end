package com.example.isaprojekat.dto;

import com.example.isaprojekat.enums.AppointmentStatus;
import com.example.isaprojekat.model.EquipmentAppointment;

import java.util.Date;

public class EquipmentAppointmentDTO {
    private Integer id;
    private Integer adminId;
    private Integer equipmentId;
    private String adminName;
    private String adminSurname;
    private Date appointmentDate;
    private String appointmentTime;
    private Integer appointmentDuration;
    public EquipmentAppointmentDTO() {
    }
    public EquipmentAppointmentDTO(EquipmentAppointment equipmentAppointment) {
        this(equipmentAppointment.getId(), equipmentAppointment.getAdminId(),
                equipmentAppointment.getEquipmentId(), equipmentAppointment.getAdminName(),
                equipmentAppointment.getAdminSurname(),
                equipmentAppointment.getAppointmentDate(), equipmentAppointment.getAppointmentTime(),
                equipmentAppointment.getAppointmentDuration());
    }
    public EquipmentAppointmentDTO(Integer id, Integer adminId, Integer equipmentId, String adminName,
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
