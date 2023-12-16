package com.example.isaprojekat.dto;

import com.example.isaprojekat.model.AppointmentReservation;
import com.example.isaprojekat.model.Item;
public class ItemDTO {
    private Integer id;
    private Integer equipmentId;
    private Integer quantity;
    private Integer reservationId;
    public ItemDTO() {
    }

    public ItemDTO(Item item) {
        this.id = item.getId();
        this.equipmentId = item.getEquipmentId();
        this.quantity = item.getQuantity();
        this.reservationId = item.getReservation();
    }

    public Integer getId() {
        return id;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getReservation() { return reservationId; }
}
