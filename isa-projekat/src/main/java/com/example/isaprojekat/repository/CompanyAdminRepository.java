package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.CompanyAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface CompanyAdminRepository extends JpaRepository<CompanyAdmin, Integer> {
    Page<CompanyAdmin> findAll(Pageable pageable);
    @Query("SELECT DISTINCT ca.user_id FROM CompanyAdmin ca")
    List<Integer> findAllUserIds();

    @Query("SELECT ca.company_id FROM CompanyAdmin ca WHERE ca.user_id = :user_id")
    Integer findCompanyIdByUserId(@Param("user_id") Integer userId);

    @Query("SELECT ca FROM CompanyAdmin ca WHERE ca.company_id = :company_id")
    List<CompanyAdmin> findByCompanyId(Integer company_id);
}
