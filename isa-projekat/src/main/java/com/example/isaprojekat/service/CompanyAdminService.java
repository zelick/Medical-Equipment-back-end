package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.CompanyAdminDTO;
import com.example.isaprojekat.dto.CompanyDTO;
import com.example.isaprojekat.dto.UserDTO;
import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.CompanyAdmin;
import com.example.isaprojekat.model.User;
import com.example.isaprojekat.repository.CompanyAdminRepository;
import com.example.isaprojekat.repository.CompanyRepository;
import com.example.isaprojekat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyAdminService {
    @Autowired
    private CompanyAdminRepository companyAdminRepository;
    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    public List<CompanyAdmin> findAll() {
        return companyAdminRepository.findAll();
    }

    //ne treba
    public CompanyAdmin createCompanyAdmin(CompanyAdminDTO companyAdminDto) {

        CompanyAdmin newCompanyAdmin = new CompanyAdmin();
        newCompanyAdmin.setCompany_id(companyAdminDto.getCompanyId());
        newCompanyAdmin.setUser_id(companyAdminDto.getUserId());
        return companyAdminRepository.save(newCompanyAdmin);
    }

    public CompanyDTO getCompanyForAdmin(Integer adminId) {
        Integer companyId = companyAdminRepository.findCompanyIdByUserId(adminId);

        // Provera da li je companyId null ili ne
        if (companyId != null) {
            Optional<Company> companyOptional = companyRepository.findById(companyId);

            // Provera da li postoji kompanija sa datim ID-em
            if (companyOptional.isPresent()) {
                Company company = companyOptional.get();
                return new CompanyDTO(company);
            }else {
                return null;
            }
        }else{
            return null;
        }
    }

    //ne treba
    public List<CompanyAdmin> createCompanyAdmins(List<CompanyAdminDTO> companyAdminDTOS){
        List<CompanyAdmin> newCompanyAdmins = new ArrayList<CompanyAdmin>();

        for (CompanyAdminDTO dto : companyAdminDTOS) {
            CompanyAdmin newCompanyAdmin = new CompanyAdmin();
            newCompanyAdmin.setCompany_id(dto.getCompanyId());
            newCompanyAdmin.setUser_id(dto.getUserId());
            newCompanyAdmins.add(newCompanyAdmin);
        }
        return companyAdminRepository.saveAll(newCompanyAdmins);
    }
    public void createCompanyAdminsForUserId(List<Integer> userIds, Integer companyId) {
        List<CompanyAdmin> newCompanyAdmins = new ArrayList<>();

        for (Integer userId : userIds) {
            CompanyAdmin newCompanyAdmin = new CompanyAdmin();
            newCompanyAdmin.setCompany_id(companyId);
            newCompanyAdmin.setUser_id(userId);
            newCompanyAdmins.add(newCompanyAdmin);
        }

        companyAdminRepository.saveAll(newCompanyAdmins);
    }
    public List<UserDTO> findUsersNotInCompanyAdmin(){
        List<UserDTO> foundUserDTOS = new ArrayList<>();
        List<Integer> userIdsInCompanyAdmin = companyAdminRepository.findAllUserIds();
        //List<User> usersNotInCompanyAdmin = userRepository.findAllByIdNotIn(userIdsInCompanyAdmin);
        List<User> usersNotInCompanyAdmin;

        if (userIdsInCompanyAdmin.isEmpty()) {
            // Ako je userIdsInCompanyAdmin prazna, dohvati sve korisnike iz baze
            usersNotInCompanyAdmin = userRepository.findAll();
        } else {
            // Inače, dohvati korisnike koji nisu u company adminu
            usersNotInCompanyAdmin = userRepository.findAllByIdNotIn(userIdsInCompanyAdmin);
        }

        for (User user : usersNotInCompanyAdmin) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setPenaltyPoints(user.getPenaltyPoints());

            foundUserDTOS.add(userDTO);
        }

        return foundUserDTOS;
    }
}
