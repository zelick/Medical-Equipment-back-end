package com.example.isaprojekat.service;

import com.example.isaprojekat.model.Contract;
import com.example.isaprojekat.repository.ContractRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
            setInvalid(contract.getHospitalId());
            contractRepository.save(contract);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setInvalid(Integer hospitalId) {
        contractRepository.setInvalidForHospital(hospitalId);
    }
    public List<Contract> getAll() {
        return contractRepository.findAll();
    }

    @Cacheable(value = "contract")
    public Contract getById(Integer id) {
        return contractRepository.findById(id).get();
    }
}
