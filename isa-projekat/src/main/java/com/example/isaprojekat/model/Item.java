package com.example.isaprojekat.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "equipmentId", nullable = false)
    private Integer equipmentId;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    public Item() {
    }
    public Item(Integer id, Integer equipmentId, Integer quantity) {
        this.id = id;
        this.equipmentId = equipmentId;
        this.quantity = quantity;
    }

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
