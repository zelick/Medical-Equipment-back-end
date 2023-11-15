package com.example.isaprojekat.service;

import com.example.isaprojekat.repository.CompanyRepository;
import com.example.isaprojekat.repository.EquipmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;
}
