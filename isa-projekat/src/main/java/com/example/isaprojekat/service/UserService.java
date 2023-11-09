package com.example.isaprojekat.service;

import com.example.isaprojekat.model.User;
import com.example.isaprojekat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User findOne(Integer id) {
        return userRepository.findById(id).orElseGet(null);
    }
    public List<User> findByFirstName(String firstName) {
        return userRepository.findAllByFirstName(firstName);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public Page<User> findAll(Pageable page) {
        return userRepository.findAll(page);
    }
    public User save(User student) {
        return userRepository.save(student);
    }

    public void remove(Integer id) {
        userRepository.deleteById(id);
    }
}
