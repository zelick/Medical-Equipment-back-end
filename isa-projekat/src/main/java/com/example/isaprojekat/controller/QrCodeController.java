package com.example.isaprojekat.controller;

import com.example.isaprojekat.service.EmailService;
import com.example.isaprojekat.service.QrCodeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/qr-code")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class QrCodeController {
    @Autowired
    private QrCodeService qrCodeService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/generate-and-send-email")
    public String generateQrCodeAndSendEmail() {
        try {
            // Generate QR Code
            BufferedImage qrCodeImage = qrCodeService.generateQRCode("Your Data Here");

            // Convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "png", baos);
            byte[] qrCodeBytes = baos.toByteArray();

            // Send email with QR Code attached
            emailService.sendEmailWithAttachment("anjakovacevic9455@gmail.com", "QR Code Email Subject", "See attached QR Code.", qrCodeBytes, "qrcode.png");

            return "Email sent successfully!";
        } catch (Exception e) {
            return "Error sending email: " + e.getMessage();
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

            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error handling file upload", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/proba")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> proba() {
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }
}
