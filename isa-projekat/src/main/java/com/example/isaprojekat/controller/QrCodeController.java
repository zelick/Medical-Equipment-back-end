package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.CompanyDTO;
import com.example.isaprojekat.dto.ReservationDTO;
import com.example.isaprojekat.service.EmailService;
import com.example.isaprojekat.service.QrCodeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//dodala:
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("api/qr-code")
public class QrCodeController {
    @Autowired
    private QrCodeService qrCodeService;

    @PostMapping(value = "/generateQrCodeSendMail")
    @CrossOrigin(origins = "http://localhost:4200")
    public String generateQrCodeAndSendEmail(@RequestBody Integer reservationId) {
        if (qrCodeService.generateQRCodeSendMail(reservationId)) {
            return "Succesfully created QR CODE";
        }
        else {
            return "Error while creating QR CODE";
        }
    }

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
