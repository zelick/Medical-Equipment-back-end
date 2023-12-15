package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.CompanyDTO;
import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.Equipment;
import com.example.isaprojekat.model.User;
import com.example.isaprojekat.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

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
       // existingCompany.setEquipments(companyDTO.getEquipments());

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
