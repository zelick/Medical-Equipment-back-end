package com.example.isaprojekat.dto;

import com.example.isaprojekat.model.Equipment;

public class EquipmentDTO {
    private Integer id;
    private String name;
    private String description;
    private double price;
    private double grade;
    private String type;

    // Nema polje za Set<Company> companies, jer u DTO-u obično ne prenosimo kompleksne veze

    public EquipmentDTO() {
    }

    public EquipmentDTO(Equipment equipment) {
        this(equipment.getId(), equipment.getName(),
                equipment.getDescription(), equipment.getPrice(),
                equipment.getGrade(), equipment.getType());
    }

    public EquipmentDTO(Integer id, String name, String description, double price, double grade, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.grade = grade;
        this.type = type;
    }

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
}
