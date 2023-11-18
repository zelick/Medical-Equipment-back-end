package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.CompanyAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface CompanyAdminRepository extends JpaRepository<CompanyAdmin, Integer> {
    Page<CompanyAdmin> findAll(Pageable pageable);
    @Query("SELECT DISTINCT ca.user_id FROM CompanyAdmin ca")
    List<Integer> findAllUserIds();

}
