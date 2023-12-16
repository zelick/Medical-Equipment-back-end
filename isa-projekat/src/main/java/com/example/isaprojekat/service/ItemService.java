package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.ItemDTO;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Item save(Item item){
        return itemRepository.save(item);
    }
    public Item getByReservationId(Integer id){return itemRepository.getByReservationId(id);}
    public Item getById(Integer id){return itemRepository.getById(id);}
    public List<Item> getItemsByReservationId(Integer id){return itemRepository.getItemsByReservationId(id);}
}