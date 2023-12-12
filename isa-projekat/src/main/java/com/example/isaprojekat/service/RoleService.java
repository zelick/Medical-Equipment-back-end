package com.example.isaprojekat.service;

import com.example.isaprojekat.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import com.example.isaprojekat.model.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;
    Role findById(Integer id){
        Role auth = this.roleRepository.getOne(id);
        return auth;
    }
    List<Role> findByName(String name){
        List<Role> roles = this.roleRepository.findByName(name);
        return roles;
    }
}
