package com.example.isaprojekat.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class AppointmentReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "appointmentDate", nullable = false)
    private LocalDateTime appointmentDate;
    @Column(name = "appointmentTime", nullable = false)
    private String appointmentTime;
    @Column(name = "appointmentDuration", nullable = false)
    private Integer appointmentDuration;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Item> items = new ArrayList<Item>();

    public AppointmentReservation() {
        this.items = new ArrayList<>();
    }

    public AppointmentReservation(LocalDateTime appointmentDate, String appointmentTime, Integer appointmentDuration, User user) {
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentDuration = appointmentDuration;
        this.user = user;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Integer getAppointmentDuration() {
        return appointmentDuration;
    }

    public void setAppointmentDuration(Integer appointmentDuration) {
        this.appointmentDuration = appointmentDuration;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void addItem(Item item) {
        items.add(item);
        item.setReservation(this);
    }

    public void removeItem(Item item) {
        items.remove(item);
        item.setReservation(null);
    }
    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }
}
