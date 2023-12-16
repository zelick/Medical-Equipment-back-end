package com.example.isaprojekat.repository;

import com.example.isaprojekat.model.AppointmentReservation;
import com.example.isaprojekat.model.Company;
import com.example.isaprojekat.model.EquipmentAppointment;
import com.example.isaprojekat.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface ItemRepository extends JpaRepository<Item, Integer>{
    Item getById(Integer id);
    Item getByReservationId(Integer id);
    List<Item> getItemsByReservationId(Integer id);
}
