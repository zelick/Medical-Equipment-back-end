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
    private List<EquipmentDTO> equipments;

    private String workTimeBegin;

    private String workTimeEnd;

    public CompanyDTO() {
    }

    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.address = company.getAddress();
        this.description = company.getDescription();
        this.averageGrade = company.getAverageGrade();

        // Mapiraj opremu u DTO format
        this.equipments = company.getEquipments()
                .stream()
                .map(EquipmentDTO::new)
                .collect(Collectors.toList());

        this.workTimeBegin = company.getWorkTimeBegin();
        this.workTimeEnd = company.getWorkTimeEnd();
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

    public List<EquipmentDTO> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<EquipmentDTO> equipments) {
        this.equipments = equipments;
    }

    public String getWorkTimeBegin() {
        return workTimeBegin;
    }

    public void setWorkTimeBegin(String workTimeBegin) {
        this.workTimeBegin = workTimeBegin;
    }

    public String getWorkTimeEnd() {
        return workTimeEnd;
    }

    public void setWorkTimeEnd(String workTimeEnd) {
        this.workTimeEnd = workTimeEnd;
    }
}
