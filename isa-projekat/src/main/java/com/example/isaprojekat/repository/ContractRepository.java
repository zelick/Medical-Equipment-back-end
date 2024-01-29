package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.Contract;
import com.example.isaprojekat.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
}
