package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.ItemDTO;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Item createItem(ItemDTO itemDto) {
        Item newItem = new Item();
        newItem.setEquipmentId(itemDto.getEquipmentId());
        newItem.setQuantity(itemDto.getQuantity());
        newItem.setReservation(itemDto.getReservation());
        return itemRepository.save(newItem);
    }
}