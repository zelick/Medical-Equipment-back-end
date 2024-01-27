package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

@Repository
//@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    public Page<User> findAll(Pageable pageable);
   // public  List<User> findAll(); //nez jel radi
    public List<User> findAllByFirstName(String firstName);
    Optional<UserDetails> findByEmail(String email);
    public User findUserByEmail(String email);


    //@Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.isEnabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);
    public List<User> findAllByIdNotIn(List<Integer> userIds);

}
