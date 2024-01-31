package com.example.isaprojekat.dto;

import com.example.isaprojekat.model.Contract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractDTO {
    private Integer id;
    private String type;
    private Integer quantity;
    private LocalDate date;
    private boolean valid;

    public ContractDTO(Contract contract) {
        id = contract.getId();
        type = contract.getType();
        quantity = contract.getQuantity();
        date = contract.getDate();
        valid = contract.isValid();
    }

    public static List<ContractDTO> contractsToDtos(List<Contract> contracts) {
        List<ContractDTO> dtos = new ArrayList<>();
        for(var c : contracts) {
            dtos.add(new ContractDTO(c));
        }
        return dtos;
    }
}
