package com.example.isaprojekat.dto;

import com.example.isaprojekat.model.Equipment;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.model.Reservation;

public class ItemDTO {
    private Integer id;
    private Equipment equipment;
    private Integer quantity;
    private Reservation reservation;
    public ItemDTO() {
    }

    public ItemDTO(Item item) {
        this.id = item.getId();
        this.quantity = item.getQuantity();
        this.equipment = item.getEquipment();
        this.reservation = item.getReservation();
    }

    public Integer getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
