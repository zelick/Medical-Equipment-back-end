package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.Equipment;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
//@Transactional(readOnly = true)
public interface CompanyRepository extends JpaRepository<Company, Integer> {
   // Optional<Company> findByAdminId(Integer adminId);
   Company save(Company company );

   @Query("SELECT c FROM Company c WHERE c.id = :id")
   Company findCById(@Param("id") Integer id);
}