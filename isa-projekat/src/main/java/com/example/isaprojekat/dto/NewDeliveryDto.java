package com.example.isaprojekat.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class NewDeliveryDto {

    @NotBlank
    List<Double> startLocation;

    @NotBlank
    List<Double> endLocation;

    @NotBlank
    int frequency;
}
