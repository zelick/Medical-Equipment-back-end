package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.Contract;
import com.example.isaprojekat.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Contract c SET c.valid = false WHERE c.hospitalId = :hospitalId")
    void setInvalidForHospital(Integer hospitalId);
}
