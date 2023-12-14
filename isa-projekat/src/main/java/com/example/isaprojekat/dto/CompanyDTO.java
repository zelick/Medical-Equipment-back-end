package com.example.isaprojekat.dto;

import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.CompanyAdmin;
import com.example.isaprojekat.model.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

public class CompanyDTO {
    private Integer id;
    private String name;
    private String address;
    private String description;
    private double averageGrade;

    private Integer adminId;
    public CompanyDTO() {
    }
    public CompanyDTO(Company company) {
        this(company.getId(), company.getName(),
                company.getAddress(), company.getDescription(),
                company.getAverageGrade(), company.getAdminId());
    }

    public CompanyDTO(Integer id, String name, String address, String description, double averageGrade, Integer adminId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.averageGrade = averageGrade;
        this.adminId = adminId;
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

    public Integer getAdminId() { return adminId; }
}
