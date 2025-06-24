package com.springBoot.reactive.services;

import com.springBoot.reactive.entity.Book;
import com.springBoot.reactive.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


@SpringBootTest
public class RepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findMethodTest()
    {
//        System.out.println("Test Successfull");

//        Mono<Book> byName = bookRepository.findByName("Effective Java");
//        StepVerifier.create(byName).expectNextCount(1).verifyComplete();

        Flux<Book> byNameAndAuthor = bookRepository.findByNameAndAuthor("Effective Java", "Joshua Bloch");
        StepVerifier.create(byNameAndAuthor).expectNextCount(1).verifyComplete();

    }


    @Test
    public void queryMethodsTest()
    {
//        bookRepository.getAllBooksByAuthor("cobra" , "python")
//                .as(StepVerifier::create)
//                .expectNextCount(1)
//                .verifyComplete();
//

        bookRepository.searchBookByTitle("%java%")
                .as(StepVerifier::create)
                .expectNextCount(1)  // or more depending on how many matches
                .verifyComplete();

    }

}
