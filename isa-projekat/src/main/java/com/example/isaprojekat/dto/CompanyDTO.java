package com.example.isaprojekat.dto;

import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.Equipment;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CompanyDTO {
    private Integer id;
    private String name;
    private String address;
    private String description;
    private double averageGrade;
    private Integer adminId;

    private List<EquipmentDTO> equipments;

    public CompanyDTO() {
    }

    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.address = company.getAddress();
        this.description = company.getDescription();
        this.averageGrade = company.getAverageGrade();
        this.adminId = company.getAdminId();

        // Mapiraj opremu u DTO format
        this.equipments = company.getEquipments()
                .stream()
                .map(EquipmentDTO::new)
                .collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public List<EquipmentDTO> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<EquipmentDTO> equipments) {
        this.equipments = equipments;
    }
}
