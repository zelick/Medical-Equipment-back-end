package com.example.isaprojekat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "hospital_id", nullable = false)
    private Integer hospitalId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "valid", nullable = false)
    private boolean valid;

}

