package com.springBoot.reactive.controllers;

import com.springBoot.reactive.DTO.ApiResponse;
import com.springBoot.reactive.entity.Book;
import com.springBoot.reactive.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin("*")
public class BookController {

    @Autowired
    private BookService bookService;

    //create

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    //Mono.just(...) is used to wrap a single value (already available) into a reactive Mono.
    public Mono<ResponseEntity<ApiResponse<Book>>> create(@Valid @RequestBody Book book) {
        return bookService.create(book)
                .map(savedBook -> {
                    ApiResponse<Book> response = ApiResponse.<Book>builder()
                            .status("success")
                            .message("Book created successfully")
                            .data(savedBook)
                            .build();
                    return ResponseEntity.status(HttpStatus.CREATED).body(response);
                });
    }


//    getAll

    @GetMapping
    public Mono<ResponseEntity<ApiResponse<List<Book>>>> getAll() {
        return bookService.getAll()
                .collectList()  // Convert Flux<Book> to Mono<List<Book>>
                .map(books -> {
                    ApiResponse<List<Book>> response = ApiResponse.<List<Book>>builder()
                            .status("success")
                            .message("Books fetched successfully")
                            .data(books)
                            .build();
                    return ResponseEntity.ok(response);
                });
    }



    //get

    // Get a single book by ID with proper empty handling
    @GetMapping("/{bookId}")
    public Mono<ResponseEntity<ApiResponse<Book>>> get(@PathVariable int bookId) {
        return bookService.get(bookId)
                .map(book -> {
                    ApiResponse<Book> response = ApiResponse.<Book>builder()
                            .status("success")
                            .message("Book fetched successfully")
                            .data(book)
                            .build();
                    return ResponseEntity.ok(response);
                });
    }


    //The actual HTTP response body is a String.
    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<ApiResponse<Void>>> delete(@PathVariable int bookId) {
        return bookService.delete(bookId)
                .then(Mono.just(
                        ResponseEntity.ok(
                                ApiResponse.<Void>builder()
                                        .status("success")
                                        .message("Book deleted successfully")
                                        .data(null)
                                        .build()
                        )
                ));
    }



    //update

    @PutMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<ApiResponse<Book>>> update(@PathVariable int bookId,@Valid @RequestBody Book books) {
        return bookService.update(bookId, books)
                .map(updatedBook -> ResponseEntity.ok(
                        ApiResponse.<Book>builder()
                                .status("success")
                                .message("Book updated successfully")
                                .data(updatedBook)
                                .build()
                ))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        ApiResponse.<Book>builder()
                                .status("fail")
                                .message("Book not found")
                                .data(null)
                                .build()
                ));
    }



    // Search books by title (partial match)
    //@RequestParam is used in Spring to get values from the URL query string
    @GetMapping("/search")
    //collects all the elements emitted by a Flux<T> into a List<T>, and then returns a Mono<List<T>>.
    public Mono<ResponseEntity<ApiResponse<List<Book>>>> searchBooks(@RequestParam("title") String title) {
        Flux<Book> result = bookService.searchBooks(title);

        return result.collectList()
                .map(books -> {
                    if (!books.isEmpty()) {
                        ApiResponse<List<Book>> response = ApiResponse.<List<Book>>builder()
                                .status("success")
                                .message("Books found matching: " + title)
                                .data(books)
                                .build();
                        return ResponseEntity.ok(response);
                    } else {
                        ApiResponse<List<Book>> response = ApiResponse.<List<Book>>builder()
                                .status("fail")
                                .message("No books found with title: " + title)
                                .data(Collections.emptyList())
                                .build();
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                    }
                });
    }



}
