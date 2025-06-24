package com.springBoot.reactive.repositories;

import com.springBoot.reactive.entity.Book;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book,Integer> {

    //1-using custom way- methods as per our project complex requirement

    public Mono<Book> findByName(String name);

    public Flux<Book> findByAuthor(String author);

    public Flux<Book> findByPublisher(String publisher);

    public Flux<Book> findByNameAndAuthor(String name, String author);

    //sql way -

    //    @Query("SELECT * FROM books WHERE author = :author AND name=")
//    Flux<Book> getAllBooksByAuthor(@Param("author") String author);


    @Query("SELECT * FROM books WHERE author = :author AND name = :name")
    Flux<Book> getAllBooksByAuthor(@Param("author") String author, @Param("name") String name);


    //@Param is used in repository methods (usually with @Query) to pass values into the query.
    @Query("SELECT * FROM books WHERE name LIKE :title")
    Flux<Book> searchBookByTitle(@Param("title") String title);











}
