package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.ContractDTO;
import com.example.isaprojekat.model.Contract;
import com.example.isaprojekat.service.ContractService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/contracts")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ContractController {
    @Autowired
    private ContractService contractService;

    @GetMapping
    public ResponseEntity<List<ContractDTO>> getAll() {
        List<Contract> contracts = contractService.getAll();
        return new ResponseEntity<>(ContractDTO.contractsToDtos(contracts), HttpStatus.OK);
    }

}
