package com.example.isaprojekat.service;

import com.example.isaprojekat.model.Reservation;
import com.example.isaprojekat.repository.EquipmentRepository;
import com.example.isaprojekat.repository.ItemRepository;
import com.example.isaprojekat.repository.ReservationRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import com.example.isaprojekat.model.User;
import com.example.isaprojekat.model.Item;
import com.example.isaprojekat.model.Equipment;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class QrCodeService {
    @Autowired
    private ReservationRepository reservationRepository;
    private ItemRepository itemRepository;
    private EquipmentRepository equipmentRepository;
    private EmailService emailService;
    
    public BufferedImage generateQRCode(String data) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public String readQRCode(byte[] qrCodeBytes) {
        try {
            // Convert byte array to BufferedImage
            ByteArrayInputStream bais = new ByteArrayInputStream(qrCodeBytes);
            BufferedImage bufferedImage = ImageIO.read(bais);

            // Read QR Code
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                Result result = new MultiFormatReader().decode(bitmap);
                return result.getText();
            } catch (NotFoundException e) {
                System.out.println("There is no QR code in the image");
                return null;
            }
        } catch (IOException e) {
            System.out.println("Error reading QR code image: " + e.getMessage());
            return null;
        }
    }

    public String generateQrCodeString(Reservation reservation){
        StringBuilder itemsString = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Item item : itemRepository.getItemsByReservationId(reservation.getId())) {
            itemsString.append("-");
            Equipment e = equipmentRepository.getById(item.getEquipment().getId());
            itemsString.append("Quantity: ").append(item.getQuantity()).append(", Name: ").append(e.getName()).append("\n");
        }
        return "Your reservation informations:" + "\n"+
                "Reservation number: " + reservation.getId() + "\n" +
                "AppointmentDate: " + reservation.getAppointment().getAppointmentDate() + "\n" +
                "AppointmentTime: " + reservation.getAppointment().getAppointmentTime() + "\n" +
                "AppointmentDuration: " + reservation.getAppointment().getAppointmentDuration() + "\n" +
                "Items: "+ "\n" + itemsString + "\n" +
                "Price: " + reservation.getTotalPrice() + "\n" +
                ' ';
    }

    public boolean generateQRCodeSendMail(Integer reservationId) {
        Reservation reservation = reservationRepository.getById(reservationId);
        User user = reservation.getUser();
        List<Item> items = itemRepository.getItemsByReservationId(reservation.getId());
        String reservationData = generateQrCodeString(reservation);

        try {
            BufferedImage qrCodeImage = generateQRCode(reservationData);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "png", baos);
            byte[] qrCodeBytes = baos.toByteArray();

            emailService.sendEmailWithAttachment(
                    user.getEmail(),
                    "Reservation QR Code",
                    "Please find your reservation QR Code attached.",
                    qrCodeBytes,
                    "qrcode.png"
            );
            return true;
        } catch (Exception e) {
            // Handle exceptions appropriately
            e.printStackTrace();
            return false;
        }
    }

    public byte[] displayQRcode(Integer reservationId) {
        try {
            Reservation reservation = reservationRepository.getById(reservationId);
            User user = reservation.getUser();
            List<Item> items = itemRepository.getItemsByReservationId(reservation.getId());
            String reservationData = generateQrCodeString(reservation);

            // Primer:
            BufferedImage qrCodeImage = generateQRCode(reservationData);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "png", baos);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
