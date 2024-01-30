package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.ContractDTO;
import com.example.isaprojekat.model.Contract;
import com.example.isaprojekat.repository.ContractRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ObjectMapper objectMapper;
    private final ContractRepository contractRepository;

    @RabbitListener(queues = "order")
    public void receiveMessage(Message message) {
        String json = new String(message.getBody());
        try {
            Contract contract = objectMapper.readValue(json, Contract.class);
            contract.setValid(true);
            contractRepository.save(contract);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Contract> getAll() {
        List<Contract> contracts = new ArrayList<>();
        for(var c : contractRepository.findAll()) {
            if(c.isValid()) contracts.add(c);
        }
        return contracts;
    }

    public Contract update(int contractId, ContractDTO dto) {
        Contract contract = contractRepository.findById(contractId).get();
        if(contract == null) return null;
        contract.setDate(contract.getDate().plusMonths(1));
        contractRepository.save(contract);
        return contract;
    }
}
