package com.example.isaprojekat.service;


import com.example.isaprojekat.model.Equipment;
import com.example.isaprojekat.repository.EquipmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;

    public Equipment GetOne(Integer id){return equipmentRepository.getById(id);}
    public List<Equipment> findAllByCompanies_Id(Integer id){return equipmentRepository.findAllByCompanies_Id(id);}
    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }
    public List<Equipment> getAllEquipmentWithCompanies() {
        return equipmentRepository.findAllWithCompanies();
    }

}
