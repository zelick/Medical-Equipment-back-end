package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.ContractDTO;
import com.example.isaprojekat.model.Contract;
import com.example.isaprojekat.service.ContractService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "{contractId}")
    public ResponseEntity<ContractDTO> update(@PathVariable int contractId, @RequestBody ContractDTO dto) {
        Contract c = contractService.update(contractId, dto);
        return c == null ? new ResponseEntity<>(null, HttpStatus.BAD_REQUEST) : new ResponseEntity<>(new ContractDTO(c), HttpStatus.OK);
    }


}
