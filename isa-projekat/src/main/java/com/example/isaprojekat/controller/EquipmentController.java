package com.example.isaprojekat.controller;

import com.example.isaprojekat.service.CompanyService;
import com.example.isaprojekat.service.EquipmentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/equipment")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;
}
