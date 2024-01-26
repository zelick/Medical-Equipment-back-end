package com.example.isaprojekat.dto;

import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.Equipment;

public class EquipmentDTO {
    private Integer id;
    private String name;
    private String description;
    private double price;
    private double grade;
    private String type;
    private Integer maxQuantity;
    private Company company;

    public EquipmentDTO() {
    }

    public EquipmentDTO(Equipment equipment) {
        this.id = equipment.getId();
        this.name = equipment.getName();
        this.description = equipment.getDescription();
        this.price = equipment.getPrice();
        this.grade = equipment.getGrade();
        this.type = equipment.getType();
        this.maxQuantity = equipment.getMaxQuantity();
        this.company = equipment.getCompany();
    }

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public double getGrade() {
        return grade;
    }

    public String getType() {
        return type;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public Company getCompany() {
        return company;
    }
}
