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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CompanyAdminService {
    @Autowired
    private CompanyAdminRepository companyAdminRepository;
    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private UserService userService;
    public List<CompanyAdmin> findAll() {
        return companyAdminRepository.findAll();
    }

    public Company getCompanyForAdmin(Integer adminId) {
        Integer companyId = companyAdminRepository.findCompanyIdByUserId(adminId);

        if (companyId != null) {
            return companyRepository.findById(companyId).orElse(null);
        }

        return null;
    }

    public List<UserDTO> getAdminsForCompany(Integer companyId){
        List<CompanyAdmin> companyAdmins = companyAdminRepository.findByCompanyId(companyId);

        List<Integer> adminUserIds = companyAdmins.stream()
                .map(CompanyAdmin::getUser_id)
                .collect(Collectors.toList());

        List<User> admins = new ArrayList<>();
        for (Integer userId : adminUserIds) {
            User newUser = userService.findOne(userId);
            admins.add(newUser);
        }

        List<UserDTO> adminDTOs = new ArrayList<>();
        for (User admin : admins) {
            adminDTOs.add(new UserDTO(admin));
        }

        return adminDTOs;
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
        List<User> usersNotInCompanyAdmin;

        if (userIdsInCompanyAdmin.isEmpty()) {
            usersNotInCompanyAdmin = userRepository.findAll();
        } else {
            usersNotInCompanyAdmin = userRepository.findAllByIdNotIn(userIdsInCompanyAdmin);
        }

        for (User user : usersNotInCompanyAdmin) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setPenaltyPoints(user.getPenaltyPoints());
            userDTO.setUserRole(user.getUserRole());

            foundUserDTOS.add(userDTO);
        }

        return foundUserDTOS;
    }
}