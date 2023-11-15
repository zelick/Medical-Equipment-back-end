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
    //slobodni termini za preuzimanje opreme - dodati
    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CompanyAdmin> administrations = new HashSet<CompanyAdmin>();

    @ManyToMany
    @JoinTable(
            name = "company_equipment",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id")
    )
    private Set<Equipment> equipments = new HashSet<>();

    public Company() {
    }
    public Company(String name, String address, String description, double averageGrade) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.averageGrade = averageGrade;
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

    public Set<CompanyAdmin> getAdministrations() {
        return administrations;
    }

    public void setAdministrations(Set<CompanyAdmin> administrations) {
        this.administrations = administrations;
    }
    public void addAdminstrator(CompanyAdmin admin) {
        this.administrations.add(admin);
        admin.setCompany(this);
    }
    public void removeAdministrator(CompanyAdmin admin) {
        this.administrations.remove(admin);
        admin.setCompany(null);
    }

    public Set<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(Set<Equipment> equipments) {
        this.equipments = equipments;
    }

    public void addEquipment(Equipment equipment) {
        this.equipments.add(equipment);
        equipment.getCompanies().add(this);
    }

    public void removeEquipment(Equipment equipment) {
        this.equipments.remove(equipment);
        equipment.getCompanies().remove(this);
    }
}
