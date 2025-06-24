package com.springBoot.reactive.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("Books")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private int id;
    @NotBlank(message = "Book name is required")
    @Size(min = 2, max = 100, message = "Book name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "publisher is required")
    private String publisher;

    @NotBlank(message = "author is required")
    private String author;


}
