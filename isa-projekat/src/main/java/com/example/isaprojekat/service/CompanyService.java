package com.example.isaprojekat.service;
import java.util.*;

import com.example.isaprojekat.dto.CompanyDTO;
import com.example.isaprojekat.dto.EquipmentDTO;
import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.Equipment;
import com.example.isaprojekat.model.User;
import com.example.isaprojekat.repository.CompanyRepository;
import com.example.isaprojekat.repository.EquipmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    private EquipmentRepository equipmentRepository;

    public Company findOne(Integer id) {
        return companyRepository.findById(id).orElseGet(null);
    }
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company updateCompany(Integer id, CompanyDTO companyDTO) {
        Company existingCompany = findOne(id);

        if (existingCompany == null) {
            throw new EntityNotFoundException("Company not found with ID: " + id);
        }

        existingCompany.setName(companyDTO.getName());
        existingCompany.setAddress(companyDTO.getAddress());
        existingCompany.setDescription(companyDTO.getDescription());
        existingCompany.setAverageGrade(companyDTO.getAverageGrade());
        List<EquipmentDTO> equipmentDTOList = companyDTO.getEquipments();
        Set<Equipment> newEquipments = new HashSet<>();
        for (EquipmentDTO equipmentDTO : equipmentDTOList) {
            Equipment equipment = new Equipment(equipmentDTO);
            newEquipments.add(equipment);
        }
        existingCompany.setEquipments(newEquipments);

        return companyRepository.save(existingCompany);
    }

    public Company removeEquipmentFromCompany(Integer companyId, Integer equipmentId){
        Company existingCompany = findOne(companyId);
        if (existingCompany == null) {
            throw new EntityNotFoundException("Company not found with ID: " + companyId);
        }
        Equipment equipment = equipmentRepository.findEqById(equipmentId);
        existingCompany.removeEquipment(equipment);
        if (existingCompany.getEquipments().isEmpty()) {
            existingCompany.setAverageGrade(0);
        } else {
            double totalGrade = 0;
            for (Equipment e : existingCompany.getEquipments()) {
                totalGrade += e.getGrade();
            }
            existingCompany.setAverageGrade(totalGrade / existingCompany.getEquipments().size());
        }
        return companyRepository.save(existingCompany);
    }

    public Company addEquipmentToCompany(Integer companyId, Integer equipmentId){
        Company existingCompany = findOne(companyId);
        if (existingCompany == null) {
            throw new EntityNotFoundException("Company not found with ID: " + companyId);
        }
        Equipment equipment = equipmentRepository.findEqById(equipmentId);
        existingCompany.addEquipment(equipment);
        if (existingCompany.getEquipments().isEmpty()) {
            existingCompany.setAverageGrade(0);
        } else {
            double totalGrade = 0;
            for (Equipment e : existingCompany.getEquipments()) {
                totalGrade += e.getGrade();
            }
            existingCompany.setAverageGrade(totalGrade / existingCompany.getEquipments().size());
        }
        return companyRepository.save(existingCompany);
    }

    public Company createCompany(CompanyDTO companyDTO) {

        Company newCompany = new Company();
        newCompany.setName(companyDTO.getName());
        newCompany.setAddress(companyDTO.getAddress());
        newCompany.setDescription(companyDTO.getDescription());
        newCompany.setAverageGrade(5.0);
        newCompany.setEquipments(null);
        return companyRepository.save(newCompany);
    }

    public Company findByAdminId(Integer adminId) {
        System.out.println("usao je u servis kompanije");
        return companyRepository.findByAdminId(adminId)
                .orElseThrow(() -> new EntityNotFoundException("Company not found for adminId: " + adminId));
    }

}
