package com.springBoot.reactive.service.impl;

import com.springBoot.reactive.entity.Book;
import com.springBoot.reactive.exceptions.BookAlreadyExistsException;
import com.springBoot.reactive.exceptions.BookNotFoundException;
import com.springBoot.reactive.repositories.BookRepository;
import com.springBoot.reactive.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookServiceImpl implements BookService {

    //proper exception handled here

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Mono<Book> create(Book book) {
        return bookRepository.findById(book.getId())
                .flatMap(existing -> Mono.<Book>error(new BookAlreadyExistsException("Book already exists with id: " + book.getId())))
                .switchIfEmpty(Mono.defer(() -> bookRepository.save(book)));
    }


    @Override
    public Mono<Book> get(int bookId) {
        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new BookNotFoundException("Book not found with Id:" +bookId)));

    }


    @Override
    public Flux<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Mono<Void> delete(int bookId) {
        return  bookRepository
                .findById(bookId)
                .switchIfEmpty(Mono.error(new BookNotFoundException("Cannot delete. Book not found with id: " + bookId)))
                .flatMap(book -> bookRepository.delete(book));
    }

    @Override
    public Mono<Book> update(int bookId, Book books) {
        Mono<Book> oldBook = bookRepository.findById(bookId); //old book find then update to new book


        return bookRepository.findById(bookId)
                .switchIfEmpty(Mono.error(new BookNotFoundException("Cannot update. Book not found with id: " + bookId)))
                .flatMap(book1 -> {
            book1.setName(books.getName());
            book1.setPublisher(books.getPublisher());
            book1.setDescription(books.getDescription());
            book1.setAuthor(books.getAuthor());
            return bookRepository.save(book1);
        });

    }

    @Override
    public Flux<Book> search(String query) {
        return null;
    }

    @Override
    public Flux<Book> searchBooks(String title) {
        return bookRepository.searchBookByTitle("%" + title + "%")
                .switchIfEmpty(Flux.error(new BookNotFoundException("No books found with this name: " + title)));
    }

}
