package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ItemRepository extends JpaRepository<Item, Integer>{
    Optional<Item> findById(Integer id);
    Item getByReservationId(Integer id);
    List<Item> getItemsByReservationId(Integer id);

}
