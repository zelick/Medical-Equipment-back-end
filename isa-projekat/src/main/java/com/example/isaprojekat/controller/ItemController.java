package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.ItemDTO;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/item")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/byReservation/{id}")
    public ResponseEntity<List<ItemDTO>> getByReservation(@PathVariable Integer id) {
        List<Item> items = itemService.getItemsByReservationId(id);
        List<ItemDTO> itemsDTO = new ArrayList<>();
        for (Item r: items) {
            ItemDTO itemDTO = new ItemDTO(r);
            itemsDTO.add(itemDTO);
        }
        return new ResponseEntity<>(itemsDTO, HttpStatus.OK);
    }
}