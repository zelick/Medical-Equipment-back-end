package com.example.isaprojekat.dto;

public class CompanyAdminDTO {
    private Integer id;
    private Integer user_id;
    private Integer company_id;

    public CompanyAdminDTO() {
    }

    public CompanyAdminDTO(Integer user_id, Integer company_id) {
        this.user_id = user_id;
        this.company_id = company_id;
    }
    public Integer getUserId() {
        return user_id;
    }
    public Integer getCompanyId() {
        return company_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
