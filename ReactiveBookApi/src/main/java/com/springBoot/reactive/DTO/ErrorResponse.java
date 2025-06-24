package com.springBoot.reactive.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponse {
    private String timestamp;
    private int status;
    private String error;
    private String message;

}
