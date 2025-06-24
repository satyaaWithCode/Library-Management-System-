package com.springBoot.reactive.service.impl;

import com.springBoot.reactive.DTO.EmailRequest;
import com.springBoot.reactive.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
//
//    @Override
//    public Mono<Void> sendEmail(EmailRequest request, MultipartFile file) {
//        return Mono.fromRunnable(() -> {
//            try {
//                MimeMessage message = mailSender.createMimeMessage();
//                MimeMessageHelper helper = new MimeMessageHelper(message, file != null);
//
////                helper.setFrom(String.format("%s <%s>", request.getName(), request.getFrom()));
//                helper.setFrom("sbrata341@gmail.com", request.getName() + " (" + request.getFrom() + ")");
//                helper.setTo(request.getTo());
//                helper.setSubject(" Library Message");
//                helper.setText(request.getMessage(), false);
//
//                if (file != null && !file.isEmpty()) {
//                    InputStreamSource source = new ByteArrayResource(file.getBytes());
//                    helper.addAttachment(file.getOriginalFilename(), source);
//                }
//
//                mailSender.send(message);
//            } catch (MessagingException | IOException e) {
//                throw new RuntimeException("Failed to send email", e);
//            }
//        }).subscribeOn(Schedulers.boundedElastic()).then(); // Offload blocking task
//    }

@Override
public Mono<Void> sendEmail(EmailRequest request, MultipartFile file) {
    return Mono.fromRunnable(() -> {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, file != null);

            // Set sender name and email //sending mail
            helper.setFrom("sbrata341@gmail.com", request.getName() + " (" + request.getFrom() + ")");
            helper.setTo(request.getTo());
            helper.setSubject("📬 Library Message");

            //  Set HTML content with clickable email after going mail
            String htmlContent = "<p><strong>Name:</strong> " + request.getName() + "</p>" +
                    "<p><strong>Email:</strong> <a href='mailto:" + request.getFrom() + "'>" + request.getFrom() + "</a></p>" +
                    "<p><strong>Message:</strong><br>" + request.getMessage() + "</p>";

            helper.setText(htmlContent, true); // true => HTML content

            // Attachment (if any)
            if (file != null && !file.isEmpty()) {
                InputStreamSource source = new ByteArrayResource(file.getBytes());
                helper.addAttachment(file.getOriginalFilename(), source);
            }

            mailSender.send(message);
        } catch (MessagingException | IOException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }).subscribeOn(Schedulers.boundedElastic()).then(); // Offload blocking task
}



}
