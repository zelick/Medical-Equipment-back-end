package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.ContractDTO;
import com.example.isaprojekat.dto.DeliveryDTO;
import com.example.isaprojekat.dto.EquipmentDTO;
import com.example.isaprojekat.model.Contract;
import com.example.isaprojekat.model.Equipment;
import com.example.isaprojekat.repository.ContractRepository;
import com.example.isaprojekat.repository.EquipmentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ObjectMapper objectMapper;
    private final ContractRepository contractRepository;
    private final EquipmentRepository equipmentRepository;
    private final RabbitTemplate rabbitTemplate;

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

    public void checkAndPublish(LocalDate contractDate) {
        if (contractDate.equals(LocalDate.now())) {
            String message = "Sending equipment to hospital...";
            rabbitTemplate.convertAndSend("equipment-exchange", "equipment", message);
        }
    }

    public void checkAndUpdateExistingContracts() {

        List<Contract> contracts = contractRepository.getAllValidContracts();

        contracts.forEach(contract -> {
            if (contract.getDate().equals(LocalDate.now())) {

                contract.setDate(contract.getDate().plusMonths(1));

                contractRepository.save(contract);

                Equipment equipment = equipmentRepository.findFirstByType(contract.getType()).get();

                rabbitTemplate.convertAndSend("order-exchange", "equipment.#", createNewMessage(equipment, contract.getQuantity()));
            }
        });
    }

    private String createNewMessage(Equipment equipment, Integer quantity) {
        try {
            DeliveryDTO dto = new DeliveryDTO(equipment, quantity);
            return objectMapper.writeValueAsString(dto);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "Delivery got lost :/";
        }
    }

    private void setInvalid(Integer hospitalId) {
        contractRepository.setInvalidForHospital(hospitalId);
    }
    public List<Contract> getAll() {
        List<Contract> contracts = new ArrayList<>();
        for(var c : contractRepository.findAll()) {
            if(c.isValid()) contracts.add(c);
        }
        return contracts;
    }
    private boolean inDateRange(LocalDate date) {
        return LocalDate.now().isBefore(date.minusDays(3));
    }
    public Contract update(int contractId, ContractDTO dto) {
        Contract contract = contractRepository.findById(contractId).get();
        if(!inDateRange(contract.getDate())) return null;
        contract.setDate(contract.getDate().plusMonths(1));
        contractRepository.save(contract);
        return contract;
    }

    @Cacheable(value = "contract")
    public Contract getById(Integer id) {
        return contractRepository.findById(id).get();
    }

    public void declineDelivery() {
        rabbitTemplate.convertAndSend("order-exchange", "rejection.#", "Order for this month has been declined.");
    }
}