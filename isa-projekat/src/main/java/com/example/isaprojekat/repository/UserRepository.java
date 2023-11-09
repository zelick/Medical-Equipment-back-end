package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public Page<User> findAll(Pageable pageable);
    public List<User> findAllByFirstName(String firstName);
}
