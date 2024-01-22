package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.CompanyDTO;
import com.example.isaprojekat.service.EmailService;
import com.example.isaprojekat.service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        /*try {
            qrCodeService.generateQRCodeSendMail(reservationId);
            return ResponseEntity.ok().body("Uspesno poslat qr code");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error sending email: " + e.getMessage());
        }
         */
    }
}
