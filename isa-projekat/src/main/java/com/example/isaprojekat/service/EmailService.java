package com.example.isaprojekat.service;

import com.example.isaprojekat.model.EmailSender;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


@Service
@AllArgsConstructor
public class EmailService implements EmailSender {
    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    @Autowired
    protected QrCodeService qrCodeService;
    @Override
    @Async
    public void send(String to, String email) {
        try{
             MimeMessage mimeMessage=mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("anjakovacevic9455@gmail.com");
            mailSender.send(mimeMessage);
        } catch(MessagingException e){
            LOGGER.error("failed to send email",e);
            throw new IllegalStateException("failed to send email");
        }
    }

    public void sendEmailWithAttachment(String to, String subject, String text, byte[] attachmentData, String attachmentFileName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            ByteArrayResource byteArrayResource = new ByteArrayResource(attachmentData);
            // Attach the QR code
            helper.addAttachment(attachmentFileName, byteArrayResource);

            mailSender.send(message);
        } catch (MessagingException e) {
            // Handle exceptions appropriately
            e.printStackTrace();
        }
    }
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
            this.sendEmailWithAttachment("anjakovacevic9455@gmail.com", "QR Code Email Subject", "See attached QR Code.", qrCodeBytes, "qrcode.png");

            return "Email sent successfully!";
        } catch (Exception e) {
            return "Error sending email: " + e.getMessage();
        }
    }
}
