package com.example.isaprojekat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {

    private Integer id;

    private String type;

    private Integer quantity;

    private LocalDate date;

    private boolean valid;
}
