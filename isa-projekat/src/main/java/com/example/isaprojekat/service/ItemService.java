package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.ItemDTO;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    private EquipmentService equipmentService;

    public List<Item> findAll(){
        return itemRepository.findAll();
    }
    public Item createItem(ItemDTO itemDto) {
        Item newItem = new Item();
        newItem.setQuantity(itemDto.getQuantity());
        newItem.setEquipment(itemDto.getEquipment());
        newItem.setReservation(itemDto.getReservation());
        return itemRepository.save(newItem);
    }
    public Item save(Item item){
        return itemRepository.save(item);
    }
    public Item getByReservationId(Integer id){return itemRepository.getByReservationId(id);}
    public Item getById(Integer id){return itemRepository.getById(id);}
    public List<Item> getItemsByReservationId(Integer id){return itemRepository.getItemsByReservationId(id);}
    public Optional<Item> findById(Integer id) {
        return itemRepository.findById(id);
    }

    public Item updateItem(Integer itemId, ItemDTO itemDTO) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);
        Item item = new Item();

        if (optionalItem.isPresent()) {
            item = optionalItem.get();
        }

        if (item == null) {
            throw new EntityNotFoundException("Item not found with ID: " + itemId);
        }

        item.setQuantity(itemDTO.getQuantity());
        item.setEquipment(itemDTO.getEquipment());
        item.setReservation(itemDTO.getReservation());

        equipmentService.reduceEquimentMaxQuantity(item.getEquipment(), item.getQuantity());

        return itemRepository.save(item);
    }
}