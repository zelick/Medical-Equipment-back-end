package com.example.isaprojekat.service;


import com.example.isaprojekat.dto.EquipmentDTO;
import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.Equipment;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.repository.EquipmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;

    public Equipment findOne(Integer id) {
        return equipmentRepository.findById(id).orElseGet(null);
    }

    public Equipment save(Equipment equipment) { return equipmentRepository.save(equipment); }

    public Equipment GetOne(Integer id){return equipmentRepository.getById(id);}

    public List<Equipment> findAllByCompanyId(Company company){
        List<Equipment> foundEquipment = new ArrayList<>();

        for (Equipment e : findAll()) {
            if (e.getCompany().getId().equals(company.getId())) {
                foundEquipment.add(e);
            }
        }

        return foundEquipment;
    }
    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    public Equipment updateEquipment(Integer id, EquipmentDTO equipmentDTO) {
        Equipment existingEquipment = findOne(id);

        if (existingEquipment == null) {
            throw new EntityNotFoundException("Company not found with ID: " + id);
        }

        existingEquipment.setName(equipmentDTO.getName());
        existingEquipment.setGrade(equipmentDTO.getGrade());
        existingEquipment.setDescription(equipmentDTO.getDescription());
        existingEquipment.setType(equipmentDTO.getType());
        existingEquipment.setPrice(equipmentDTO.getPrice());
        existingEquipment.setMaxQuantity(equipmentDTO.getMaxQuantity());

        return equipmentRepository.save(existingEquipment);
    }

    public Equipment findById(Integer id)
    {
        return equipmentRepository.findEqById(id);
    }

    public void reduceEquimentMaxQuantity(Equipment equipment, Integer quantity) {
        equipment.setMaxQuantity(equipment.getMaxQuantity() - quantity);
        equipmentRepository.save(equipment);
    }

    public void increaseEquimentMaxQuantity(Set<Item> reservationItems) {
        for (Item i : reservationItems) {
            Equipment equipment = i.getEquipment();
            equipment.setMaxQuantity(equipment.getMaxQuantity() + i.getQuantity());
            equipmentRepository.save(equipment);
        }
    }

    public List<Equipment> searchEquipmentByName(String name) {
        List<Equipment> foundEquipments = new ArrayList<>();

        for (Equipment e : equipmentRepository.findAll()) {
            if (name == null || e.getName().toLowerCase().contains(name.toLowerCase())) {
                foundEquipments.add(e);
            }
        }

        return foundEquipments;
    }
}
