package com.example.isaprojekat.model;

import com.example.isaprojekat.enums.UserRole;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

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

    public User(){super();}

    public User(Integer id, String firstName, String lastName,
                String email, String password, boolean isLocked,
                boolean isEnabled, UserRole userRole){
        super();
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.password=password;
        this.isEnabled=isEnabled;
        this.isLocked=isLocked;
        this.userRole=userRole;
    }

    public User(String firstName,
                String lastName,
                String email,
                String password,
                Boolean isLocked,
                Boolean isEnabled,
                UserRole userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isLocked = isLocked;
        this.isEnabled = isEnabled;
        this.userRole = userRole;
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
                UserRole appUserRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userRole = appUserRole;
    }
    public User(String firstName,
                String lastName,
                String email,
                String password,
                int role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userRole = UserRole.USER;
    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(authority);
    }
    public boolean isAccountNonExpired() {
        return true;
    }


    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

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

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
