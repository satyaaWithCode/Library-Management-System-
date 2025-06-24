package com.springBoot.reactive.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

    private String status;   // success / error
    private String message;
    private T data;
}
