package com.CRUD.service.impl;

import com.CRUD.service.EmilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImplements implements EmilService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    @Override
    public void sendEmployeeWelcomeEmail(String toEmail, String name) {
        try {
            System.out.println("EMAIL SERVICE CALLED for: " + toEmail);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Welcome to Student Registry Service");
            message.setText(
                    "Hello " + name + ",\n\n" +
                            "Welcome to Student Registry Service!\n" +
                            "Your Student account has been created successfully.\n\n" +
                            "Best Regards,\nSRS Admin"
            );
            mailSender.send(message);
            System.out.println("EMAIL SENT SUCCESSFULLY");
        } catch (Exception e) {
            System.out.println("EMAIL FAILED: " + e.getMessage());
        }
    }
}