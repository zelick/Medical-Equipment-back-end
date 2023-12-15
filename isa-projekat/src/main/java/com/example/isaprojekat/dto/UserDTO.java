package com.example.isaprojekat.dto;

import com.example.isaprojekat.enums.UserRole;
import com.example.isaprojekat.model.User;

public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private String password;
    private boolean isEnabled;
    private Double penaltyPoints;
    private String city;
    private String country;
    private String phoneNumber;
    private String occupation;
    private String companyInfo;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPenaltyPoints(),
                user.getUserRole(),
                user.getCity(),
                user.getCountry(),
                user.getPhoneNumber(),
                user.getOccupation(),
                user.getCompanyInfo(),
                user.getPassword(),
                user.getEnabled()
        );
    }

    public UserDTO(Integer id, String firstName, String lastName, Double penaltyPoints, UserRole userRole,
                   String city, String country, String phoneNumber, String occupation, String companyInfo,
                   String password, boolean isEnabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.penaltyPoints = penaltyPoints;
        this.userRole = userRole;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.occupation = occupation;

        this.companyInfo = companyInfo;
        this.password= password;
        this.isEnabled=isEnabled;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Double getPenaltyPoints() {
        return penaltyPoints;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getOccupation() {
        return occupation;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    //seteri:
    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPenaltyPoints(Double penaltyPoints) {
        this.penaltyPoints = penaltyPoints;
    }



    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
