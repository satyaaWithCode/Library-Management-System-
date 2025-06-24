package com.springBoot.reactive.service;

import com.springBoot.reactive.entity.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

    public Mono<Book> create(Book book);

    public Mono<Book> get(int bookId);

    public Flux<Book> getAll();

    public Mono<Void> delete(int bookId);

    public Mono<Book> update(int bookId , Book books);

    public Flux<Book> search(String query);

    public Flux<Book> searchBooks(String title);

    // we can add methods as per our requirement
}
