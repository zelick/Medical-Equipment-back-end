package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
//@Transactional(readOnly = true)
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {

    Equipment getById(Integer id);
    /*@Query("SELECT e FROM Equipment e WHERE e.company.id = :companyId")
    List<Equipment> findAllByCompanyId(@Param("companyId") Integer companyId);
    List<Equipment> findAllWithCompanies();*/

    @Query("SELECT e FROM Equipment e WHERE e.id = :id")
    Equipment findEqById(@Param("id") Integer id);
}
