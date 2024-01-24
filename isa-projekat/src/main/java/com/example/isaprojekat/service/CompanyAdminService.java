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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyAdminService {
    @Autowired
    private CompanyAdminRepository companyAdminRepository;
    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private UserService userService;
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

    public Company getCompanyForAdmin(Integer adminId) {
        Integer companyId = companyAdminRepository.findCompanyIdByUserId(adminId);

        if (companyId != null) {
            Optional<Company> companyOptional = companyRepository.findById(companyId);

            if (companyOptional.isPresent()) {
                return companyOptional.get();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public List<UserDTO> getAdminsForCompany(Integer companyId){
        System.out.println("Usao je u servis");
        System.out.println("Id kompanije:");
        System.out.println(companyId);
        List<CompanyAdmin> companyAdmins = companyAdminRepository.findByCompanyId(companyId);
        System.out.println("Vel liste companyAdm:");
        System.out.println(companyAdmins.size());

        // Dobavi sve korisnike (admine) na osnovu user_id iz CompanyAdmin zapisa
        List<Integer> adminUserIds = companyAdmins.stream()
                .map(CompanyAdmin::getUser_id)
                .collect(Collectors.toList());
        System.out.println("Vel liste admin user idjevi:");
        System.out.println(adminUserIds.size());
        System.out.println("Admin user id - jevi:");
        for( Integer userId : adminUserIds){
            System.out.println(userId);
        }

        // Dobavi korisnike (admine) na osnovu njihovih ID-jeva
        List<User> admins = new ArrayList<>();
        for (Integer userId : adminUserIds) {
            System.out.println("usao u metodu findone");
            System.out.println("user id koji se salje metodi findone:");
            System.out.println(userId);
            User newUser = userService.findOne(userId);
            admins.add(newUser);
        }
        System.out.println("Vel liste celi korisnici:");
        System.out.println(admins.size());


        // Mapiraj User entitete na DTO objekte
        List<UserDTO> adminDTOs = new ArrayList<>();
        for (User admin : admins) {
            adminDTOs.add(new UserDTO(admin));
        }
        System.out.println("Vel liste celi korisnici DTO:");
        System.out.println(adminDTOs.size());

        return adminDTOs;
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
            userDTO.setUserRole(user.getUserRole());

            foundUserDTOS.add(userDTO);
        }

        return foundUserDTOS;
    }
}
