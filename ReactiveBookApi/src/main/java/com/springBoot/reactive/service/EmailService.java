package com.springBoot.reactive.service;

import com.springBoot.reactive.DTO.EmailRequest;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;


public interface EmailService {
    Mono<Void> sendEmail(EmailRequest request, MultipartFile file);
}
