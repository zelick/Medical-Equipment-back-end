package com.example.isaprojekat.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "address", nullable = false)
    private String address; //promeniti na Loacation sa mape
    @Column(name = "description", nullable = true)
    private String description;
    @Column(name = "averageGrade", nullable = true)
    private double averageGrade;
    @Column(name = "adminId", nullable = true)
    private Integer adminId;
    //slobodni termini za preuzimanje opreme - dodati

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Equipment> equipments = new HashSet<>();
    public Company() {
    }
    public Company(String name, String address, String description, double averageGrade, Integer adminId) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.averageGrade = averageGrade;
        this.adminId = adminId;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public Set<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(Set<Equipment> equipments) {
        this.equipments = equipments;
    }

    public void addEquipment(Equipment equipment) {
        this.equipments.add(equipment);
        equipment.setCompany(this);
        recalculateAverageGrade();
    }

    public void removeEquipment(Equipment equipment) {
        this.equipments.remove(equipment);
        equipment.setCompany(null);
        recalculateAverageGrade();
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    private void recalculateAverageGrade() {
        if (equipments.isEmpty()) {
            this.averageGrade = 0;
        } else {
            double totalGrade = 0;
            for (Equipment equipment : equipments) {
                totalGrade += equipment.getGrade();
            }
            this.averageGrade = totalGrade / equipments.size();
        }
    }
}
