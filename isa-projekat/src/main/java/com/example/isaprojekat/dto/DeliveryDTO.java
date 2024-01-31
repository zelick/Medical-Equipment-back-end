package com.example.isaprojekat.dto;

import com.example.isaprojekat.model.Equipment;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryDTO {

    private String name;

    private String type;

    private Integer quantity;

    public DeliveryDTO(Equipment equipment, Integer quantity) {
        name = equipment.getName();
        type = equipment.getType();
        this.quantity = quantity;
    }
}
