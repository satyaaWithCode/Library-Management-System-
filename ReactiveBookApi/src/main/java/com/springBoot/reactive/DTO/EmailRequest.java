package com.springBoot.reactive.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

    @NotBlank(message = "Sender name is required")
    private String name;

    @NotBlank(message = "Sender email is required")
    @Email(message = "Invalid sender email")
    private String from;

    @NotBlank(message = "Receiver email is required")
    @Email(message = "Invalid receiver email")
    private String to;

    @NotBlank(message = "Message is required")
    private String message;

}
