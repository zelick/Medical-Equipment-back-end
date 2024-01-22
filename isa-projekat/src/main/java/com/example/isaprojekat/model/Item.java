package com.example.isaprojekat.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne(optional = true)
    @JoinColumn(name = "reservation_id", nullable = true)
    private Reservation reservation;

    public Item() {
    }
    public Item(Integer id, Equipment equipment, Integer quantity, Reservation reservation) {
        this.id = id;
        this.equipment = equipment;
        this.quantity = quantity;
        this.reservation = reservation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
