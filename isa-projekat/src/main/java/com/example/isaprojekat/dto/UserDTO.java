package com.example.isaprojekat.dto;

import com.example.isaprojekat.enums.UserRole;
import com.example.isaprojekat.model.User;

public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private Double penaltyPoints;
    private UserRole userRole;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getPenaltyPoints(), user.getUserRole());
    }
    public UserDTO(Integer id, String firstName, String lastName, Double penaltyPoints, UserRole userRole) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userRole = userRole;
        this.penaltyPoints = penaltyPoints;
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

    public Double getPenaltyPoints() { return penaltyPoints; }

    public UserRole getUserRole() { return userRole; }
}
