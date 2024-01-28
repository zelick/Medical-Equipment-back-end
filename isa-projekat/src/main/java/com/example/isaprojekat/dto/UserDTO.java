package com.example.isaprojekat.dto;

import com.example.isaprojekat.enums.UserRole;
import com.example.isaprojekat.model.User;

public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private String email;
    private String password;
    private boolean isEnabled;
    private Double penaltyPoints;
    private String city;
    private String country;
    private String phoneNumber;
    private String occupation;
    private Boolean isUserFirstLogged;
    private Boolean isLocked;

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
                user.getPassword(),
                user.getEnabled(),
                user.getEmail(),
                user.getUserFirstLogged(),
                user.getLocked()
        );
    }
    
    public UserDTO(Integer id, String firstName, String lastName, Double penaltyPoints, UserRole userRole,
                   String city, String country, String phoneNumber, String occupation,
                   String password, boolean isEnabled, String email,boolean isUserFirstLogged,boolean isLocked) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.penaltyPoints = penaltyPoints;
        this.userRole = userRole;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.occupation = occupation;
        this.email=email;
        this.password= password;
        this.isEnabled=isEnabled;
        this.isUserFirstLogged = isUserFirstLogged;
        this.isLocked = isLocked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
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

    //change password

    public Boolean getUserFirstLogged() {
        return isUserFirstLogged;
    }

    public void setUserFirstLogged(Boolean userFirstLogged) {
        isUserFirstLogged = userFirstLogged;
    }
}
