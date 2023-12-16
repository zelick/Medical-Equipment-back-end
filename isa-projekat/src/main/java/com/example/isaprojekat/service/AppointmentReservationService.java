package com.example.isaprojekat.service;

import com.example.isaprojekat.dto.AppointmentReservationDTO;
import com.example.isaprojekat.dto.ItemDTO;
import com.example.isaprojekat.model.*;
import com.example.isaprojekat.repository.AppointmentReservationRepository;
import com.example.isaprojekat.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AppointmentReservationService {
    @Autowired
    private AppointmentReservationRepository reservationRepository;
    @Autowired ItemService itemService;
    @Autowired
    private QrCodeService qrCodeService;
    @Autowired
    private EquipmentAppointmentService equipmentAppointmentService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EquipmentService equipmentService;
    public AppointmentReservation getById(Integer id){
        return reservationRepository.getById(id);
    }
    public List<AppointmentReservation> findAll() {
        return reservationRepository.findAll();
    }
    public AppointmentReservation createReservation(AppointmentReservationDTO reservationDTO) throws ParseException {

        AppointmentReservation newReservation = new AppointmentReservation();
        newReservation.setAppointmentDate(reservationDTO.getAppointmentDate());
        newReservation.setAppointmentTime(reservationDTO.getAppointmentTime());
        newReservation.setAppointmentDuration(reservationDTO.getAppointmentDuration());
        newReservation.setUser(reservationDTO.getUser());

        AppointmentReservation res = reservationRepository.save(newReservation);

        //List<EquipmentAppointment> appointments = equipmentAppointmentService.findAvailableAppointments(res.getItems());
        //sendReservationQRCodeByEmail(res.getId(),"anjakovacevic9455@gmail.com");
        return res;
    }
    public String generateQrCodeString(AppointmentReservationDTO res){
        StringBuilder itemsString = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Item item : itemService.getItemsByReservationId(res.getId())) {
            itemsString.append("-");
            Equipment e =equipmentService.GetOne(item.getEquipmentId());
            itemsString.append("Quantity: ").append(item.getQuantity()).append(", Name: ").append(e.getName()).append("\n");
        }
        //if (!res.getItems().isEmpty()) {
        //    itemsString.delete(itemsString.length() - 2, itemsString.length()); // Remove the last comma and space
       // }
        return "Your reservation informations:" + "\n"+
                "AppointmentDate: " + res.getAppointmentDate().format(formatter) + "\n" +
                "AppointmentTime=" + res.getAppointmentTime() + "\n" +
                "AppointmentDuration=" + res.getAppointmentDuration() + "\n" +
                "Items: "+ "\n" + itemsString + "\n" +
                ' ';
    }
    public void AddReservationToItem(Item item, Integer reservationId){
        item.setReservation(reservationId);
        itemService.save(item);
    }

    public List<AppointmentReservation> GetAllReservationsForUser(User user){
        return reservationRepository.getAppointmentReservationsByUser(user);
    }
    public void sendReservationQRCodeByEmail(Integer reservationId, String recipientEmail) {
        // Fetch the reservation from the database
        AppointmentReservation reservation = getById(reservationId);

        // Additional information
        User user = reservation.getUser();
        List<Item> items = itemService.getItemsByReservationId(reservation.getId());
        // Add any other information you want

        // Create a DTO (Data Transfer Object) to represent the reservation with additional information
        AppointmentReservationDTO reservationDto = new AppointmentReservationDTO();
        reservationDto.setId(reservation.getId());
        reservationDto.setAppointmentDate(reservation.getAppointmentDate());
        reservationDto.setAppointmentTime(reservation.getAppointmentTime());
        reservationDto.setAppointmentDuration(reservation.getAppointmentDuration());
        //reservationDto.setUserName(user.getName()); // Assuming there's a getName() method in the User class
        //reservationDto.setItems(items); // Assuming there's a getter for items in the ReservationDto class

        // Convert the reservation DTO to a string (you might want to customize this based on your needs)
        String reservationData = generateQrCodeString(reservationDto);

        // Generate QR code
        try {
            BufferedImage qrCodeImage = qrCodeService.generateQRCode(reservationData);

            // Convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage, "png", baos);
            byte[] qrCodeBytes = baos.toByteArray();
            //emailService.generateQrCodeAndSendEmail(

            //);

            // Send email with QR Code attached
            emailService.sendEmailWithAttachment(
                    user.getEmail(),
                    "Reservation QR Code",
                    "Please find your reservation QR Code attached.",
                    qrCodeBytes,
                    "qrcode.png"
            );
        } catch (Exception e) {
            // Handle exceptions appropriately
            e.printStackTrace();
        }
    }

}
