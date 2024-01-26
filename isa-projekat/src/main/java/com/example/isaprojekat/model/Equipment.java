package com.example.isaprojekat.model;

import com.example.isaprojekat.dto.EquipmentDTO;

import javax.persistence.*;

@Entity
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "grade", nullable = true)
    private double grade;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "maxQunatity", nullable = false)
    private Integer maxQuantity;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Version
    private Integer version;

    public Equipment() {
    }

    public Equipment(String name, String description, double price, double grade, String type, Integer maxQuantity, Company company) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.grade = grade;
        this.type = type;
        this.maxQuantity = maxQuantity;
        this.company = company;
    }

    public Equipment(EquipmentDTO equipmentDTO) {
        this.id = equipmentDTO.getId();
        this.name = equipmentDTO.getName();
        this.description = equipmentDTO.getDescription();
        this.price = equipmentDTO.getPrice();
        this.grade = equipmentDTO.getGrade();
        this.type = equipmentDTO.getType();
        this.maxQuantity = equipmentDTO.getMaxQuantity();
    }

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
