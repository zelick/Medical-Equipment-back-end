package com.example.isaprojekat.controller;

import com.example.isaprojekat.service.EmailService;
import com.example.isaprojekat.service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/qr-code")
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
}
