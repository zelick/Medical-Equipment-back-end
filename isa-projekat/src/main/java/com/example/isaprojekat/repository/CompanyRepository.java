package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
