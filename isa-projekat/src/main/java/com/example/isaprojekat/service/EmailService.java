package com.example.isaprojekat.service;

import com.example.isaprojekat.model.EmailSender;
import com.example.isaprojekat.model.Equipment;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.model.Reservation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
@Transactional
public class EmailService implements EmailSender {
    private final static Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);
    private final JavaMailSender mailSender;
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

    public void sendConfirmationReservationEmail(Reservation reservation) {
        String to = reservation.getUser().getEmail(); // Adresa korisnika
        String subject = "Confirmation of Reservation Pickup";

        // Tekst poruke
        String text = "Dear " + reservation.getUser().getFirstName() + ",\n\n" +
                "We are pleased to inform you that your reservation with Reservation Number " +
                reservation.getId() + " has been successfully picked up. Below are the details:\n\n";

        // Dodajte detalje za svaki item u rezervaciji
        for (Item item : reservation.getItems()) {
            Equipment equipment = item.getEquipment();
            if (equipment != null) {
                text += "Item: " + equipment.getName() + "\n" +
                        "Quantity: " + item.getQuantity() + "\n\n";
            }
        }
        // Dodajte završne poruke
        text += "Thank you! Hope to see you soon!\n";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            mailSender.send(message);
        } catch (MessagingException e) {
            // Obradite izuzetak kako vam odgovara
            e.printStackTrace();
        }
    }

}
