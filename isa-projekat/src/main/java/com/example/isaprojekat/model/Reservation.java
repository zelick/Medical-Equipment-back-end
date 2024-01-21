package com.example.isaprojekat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "appointmentId")
    private Appointment appointment;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.PERSIST, orphanRemoval = false)
    @JsonIgnore
    private Set<Item> items = new HashSet<>();
    public Reservation() {

    }

    public Reservation(Appointment appointment, User user, Set<Item> items) {
        this.appointment = appointment;
        this.user = user;
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
