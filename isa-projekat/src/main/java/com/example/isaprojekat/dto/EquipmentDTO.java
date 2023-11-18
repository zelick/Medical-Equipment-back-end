package com.example.isaprojekat.dto;

import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.Equipment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EquipmentDTO {
    private Integer id;
    private String name;
    private String description;
    private double price;
    private double grade;
    private String type;

    private List<CompanyDTO> companies = new ArrayList<>();
    public EquipmentDTO() {
    }

    /*public EquipmentDTO(Equipment equipment) {
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
        //this.companies = convertCompaniesToDTOs(companiesSet);
        this.companies = new ArrayList<>();
    }*/
    public EquipmentDTO(Equipment equipment) {
        this.id = equipment.getId();
        this.name = equipment.getName();
        this.description = equipment.getDescription();
        this.price = equipment.getPrice();
        this.grade = equipment.getGrade();
        this.type = equipment.getType();
        this.companies = convertCompaniesToDTOs(equipment.getCompaniesSet());
    }

    private List<CompanyDTO> convertCompaniesToDTOs(Set<Company> companies) {
        List<CompanyDTO> companyDTOs = new ArrayList<>();
        for (Company company : companies) {
            companyDTOs.add(new CompanyDTO(company));
        }
        return companyDTOs;
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

    public void setCompanies(List<CompanyDTO> companies) {
        this.companies = companies;
    }
    public List<CompanyDTO> getCompanies() {
        return companies;
    }
}
