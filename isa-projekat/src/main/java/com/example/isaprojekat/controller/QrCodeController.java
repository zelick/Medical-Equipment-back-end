package com.example.isaprojekat.controller;

import com.example.isaprojekat.dto.CompanyDTO;
import com.example.isaprojekat.service.EmailService;
import com.example.isaprojekat.service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    }

    @GetMapping("/getQRCodeData/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<byte[]> getQRCodeImage(@PathVariable Integer id) {
        try {
            byte[] qrCodeBytes = qrCodeService.displayQRcode(id);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCodeBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
