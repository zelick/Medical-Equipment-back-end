package com.example.isaprojekat.model;

import com.example.isaprojekat.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

import java.lang.Double;
import java.util.List;


@Entity
@EqualsAndHashCode
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "isLocked", nullable = false)
    private Boolean isLocked =false;

    @Column(name = "isEnabled", nullable = false)
    private Boolean isEnabled = false;

    @Column(name = "userRole", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "penaltyPoints", nullable = false)
    private Double penaltyPoints = 0.0;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "phoneNumber", nullable = false)
    private String phoneNumber;

    @Column(name = "occupation", nullable = false)
    private String occupation;

    @Column(name = "companyInfo", nullable = false)
    private String companyInfo;

    @Column(name = "is_user_first_logged", nullable = true)
    private Boolean isUserFirstLogged;

    public User(){super();}

    public User(Integer id, String firstName, String lastName,
                String email, String password, boolean isLocked,
                boolean isEnabled, UserRole userRole, Double penaltyPoints,
                String city,
                String country,
                String phoneNumber,
                String occupation,
                String companyInfo, boolean isUserFirstLogged){
        super();
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
        this.isEnabled=isEnabled;
        this.isLocked=isLocked;
        this.userRole=userRole;
        this.penaltyPoints = penaltyPoints;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.occupation = occupation;
        this.companyInfo = companyInfo;
        this.isUserFirstLogged = isUserFirstLogged;
    }

    public User(String firstName,
                String lastName,
                String email,
                String password,
                Boolean isLocked,
                Boolean isEnabled,
                UserRole userRole,
                String city,
                String country,
                String phoneNumber,
                String occupation,
                String companyInfo,
                Boolean isUserFirstLogged) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isLocked = isLocked;
        this.isEnabled = isEnabled;
        this.userRole = userRole;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.occupation = occupation;
        this.companyInfo = companyInfo;
        this.isUserFirstLogged = isUserFirstLogged;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getCompanyInfo() {
        return companyInfo;
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }
    public String getUsername(){
        return email;
    }
    public void setUsername(String username){
        this.email=username;
    }
    public User(String firstName,
                String lastName,
                String email,

                String password,
                UserRole appUserRole,
                String city,
                String country,
                String phoneNumber,
                String occupation,
                String companyInfo,
                boolean isUserFirstLogged) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userRole = appUserRole;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.occupation = occupation;
        this.companyInfo = companyInfo;
        this.isUserFirstLogged = isUserFirstLogged;
    }
    public User(String firstName,
                String lastName,
                String email,
                String password,
                int role,
                String city,
                String country,
                String phoneNumber,
                String occupation,
                String companyInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userRole = UserRole.USER;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.occupation = occupation;
        this.companyInfo = companyInfo;
    }

    /*
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }
     */


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }


    public Double getPenaltyPoints() { return penaltyPoints; }
    public void setPenaltyPoints(Double penalityPoints) { this.penaltyPoints = penalityPoints; }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    //changed password
    public void setUserFirstLogged(Boolean userFirstLogged) {
        isUserFirstLogged = userFirstLogged;
    }
    public Boolean getUserFirstLogged() {
        return isUserFirstLogged;
    }
}
