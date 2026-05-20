package com.example.demo.service;



import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ByteArrayResource;

import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import org.thymeleaf.TemplateEngine;

import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private PdfService pdfService;

    @Async
    public void sendTicketEmail(

            String email,

            String bookingId,

            String source,

            String destination,

            String date,

            String busName,

            String seats,

            String arrivalTime

    ) {

        try {

            String qrData =
                    "BOOKING ID : " + bookingId;

            String qrCode =
                    qrCodeService.generateQRCode(qrData);

            Context context = new Context();

            context.setVariable("qrCode", qrCode);

            context.setVariable(
                    "bookingId",
                    bookingId
            );

            context.setVariable(
                    "source",
                    source
            );

            context.setVariable(
                    "destination",
                    destination
            );

            context.setVariable(
                    "date",
                    date
            );

            context.setVariable(
                    "busName",
                    busName
            );

            context.setVariable(
                    "seats",
                    seats
            );

            context.setVariable(
                    "arrivalTime",
                    arrivalTime
            );

            String html =
                    templateEngine.process(
                            "ticket-pdf",
                            context
                    );

            byte[] pdf =
                    pdfService.generatePdf(html);

            MimeMessage message =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            message,
                            true
                    );

            helper.setTo(email);

            helper.setSubject(
                    "Your Bus Ticket"
            );

            helper.setText(
                    "Your booking has been confirmed.\n\n" +

                            "Bus : " + busName + "\n" +

                            "From : " + source + "\n" +

                            "To : " + destination + "\n" +

                            "Seats : " + seats + "\n\n" +

                            "Please find your ticket attached as PDF."
            );

            helper.addAttachment(
                    "ticket.pdf",
                    new ByteArrayResource(pdf)
            );

            mailSender.send(message);

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }

    public void sendForgotPasswordEmail(
            String to,
            String name,
            String resetLink
    ) throws MessagingException {

        String subject =
                "Reset Your FastX Password";

        String body =
                """
                <html>
                <body style="font-family:Arial;padding:20px;">
                
                <h2>Hello %s,</h2>
    
                <p>
                We received a request to reset your FastX account password.
                </p>
    
                <a href="%s"
                   style="
                   display:inline-block;
                   padding:14px 24px;
                   background:#2563EB;
                   color:white;
                   text-decoration:none;
                   border-radius:10px;
                   font-weight:bold;
                   margin-top:20px;
                   ">
                   Reset Password
                </a>
    
                <p style="margin-top:25px;">
                This link will expire in 15 minutes.
                </p>
    
                </body>
                </html>
                """
                        .formatted(
                                name,
                                resetLink
                        );

        MimeMessage message =
                mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(
                        message,
                        true
                );

        try {

            helper.setTo(to);

            helper.setSubject(subject);

            helper.setText(
                    body,
                    true
            );

            mailSender.send(message);

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }
}