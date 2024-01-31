package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.NewDeliveryDto;
import com.example.isaprojekat.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/delivery")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class LocationController {

    private final LocationService locationService;

    @PostMapping(value = "/start")
    public String StartDelivery(@RequestBody NewDeliveryDto newDeliveryDto) {
        locationService.StartDelivery(newDeliveryDto);
        return "";
    }
}
