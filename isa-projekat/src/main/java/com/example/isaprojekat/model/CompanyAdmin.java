package com.example.isaprojekat.model;

import javax.persistence.*;

@Entity
@Table(name = "companyAdmin")
public class CompanyAdmin{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_id", nullable = false)
    private Integer user_id;
    @Column(name = "company_id", nullable = false)
    private Integer company_id;
    public CompanyAdmin() {}

    public Integer getUser_id() {
        return user_id;
    }
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }
}
