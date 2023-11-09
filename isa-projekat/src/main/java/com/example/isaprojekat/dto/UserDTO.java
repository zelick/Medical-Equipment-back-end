package com.example.isaprojekat.dto;

import com.example.isaprojekat.model.User;

public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    public UserDTO() {

    }

    public UserDTO(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName());
    }
    public UserDTO(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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
}
