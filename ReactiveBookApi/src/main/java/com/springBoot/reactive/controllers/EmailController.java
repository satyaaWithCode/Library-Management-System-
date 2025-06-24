package com.springBoot.reactive.controllers;

import com.springBoot.reactive.DTO.EmailRequest;
import com.springBoot.reactive.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor //we can use
@Validated
public class EmailController {

    private final EmailService emailService;

    @PostMapping(value = "/send", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<String>> sendEmailWithAttachment(
            @ModelAttribute @Valid  EmailRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        return emailService.sendEmail(request, file)
                .thenReturn(ResponseEntity.ok("✅ Email sent successfully"));
    }


}
