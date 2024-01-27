package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.UserDTO;
import com.example.isaprojekat.model.RegistrationRequest;
import com.example.isaprojekat.service.QrCodeService;
import com.example.isaprojekat.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path="api/registration")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {
    private final RegistrationService registrationService;
    @Autowired
    private QrCodeService qrCodeService;
    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }
    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @PostMapping(value = "/registerSystemAdmin")
    @CrossOrigin(origins = "http://localhost:4200")
    public UserDTO registerSystemAdmin(@RequestBody RegistrationRequest request) {
        return registrationService.registerSystemAdmin(request);
    }

    //ovo obrisi posle
    @PostMapping(value = "/readQrCodeImage")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("No file received", HttpStatus.BAD_REQUEST);
        }

        try {
            byte[] imageBytes = file.getBytes();

            // Read QR Code
            String qrCodeData = qrCodeService.readQRCode(imageBytes);

            // Vratite string kao odgovor
            return new ResponseEntity<>(qrCodeData, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error handling file upload", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
