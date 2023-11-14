package com.example.isaprojekat.model;

import com.example.isaprojekat.enums.UserRole;

import javax.persistence.*;

@Entity
@Table(name = "CompanyAdmin")
public class CompanyAdmin extends User{
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    public CompanyAdmin(String firstName, String lastName, String email,
                        String password, Boolean isLocked,
                        Boolean isEnabled, UserRole userRole, Company company) {

        super(firstName, lastName, email, password, isLocked, isEnabled, userRole);
        this.company = company;
    }

    public CompanyAdmin() {

    }

    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }
}
