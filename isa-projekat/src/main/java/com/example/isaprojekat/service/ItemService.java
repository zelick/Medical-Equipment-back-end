package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.ItemDTO;
import com.example.isaprojekat.model.Equipment;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.model.Reservation;
import com.example.isaprojekat.repository.EquipmentRepository;
import com.example.isaprojekat.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    private EquipmentRepository equipmentRepository;

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public void createReservationItem(Set<Item> items, Reservation reservation) {
        for (Item i : items) {
            Item item = new Item();
            item.setReservation(reservation);
            item.setQuantity(i.getQuantity());
            Equipment equipment = equipmentRepository.findEqById(i.getEquipment().getId());
            //Equipment equipment = i.getEquipment();
            equipment.setMaxQuantity(equipment.getMaxQuantity() - i.getQuantity());
            try {
                equipment = equipmentRepository.save(equipment);
                equipmentRepository.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            item.setEquipment(equipment);
            try {
                itemRepository.save(item);
                itemRepository.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Item save(Item item){
        return itemRepository.save(item);
    }

    public Optional<Item> findById(Integer id) {
        return itemRepository.findById(id);
    }
    public List<Item> getItemsByReservationId(Integer id){return itemRepository.getItemsByReservationId(id);}
}