package com.springBoot.reactive.BookDetails;

import com.springBoot.reactive.entity.Book;
import com.springBoot.reactive.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(BookRepository bookRepository) {
        return args -> {
            Book javaBook = new Book();
            javaBook.setName("Java Programming");
            javaBook.setAuthor("James Gosling");
            javaBook.setPublisher("Oracle");
            javaBook.setDescription(
                    "## Java Programming\n\n" +
                            "Java is a high-level, object-oriented language.\n\n" +
                            "### Topics Covered\n" +
                            "- Classes and Objects\n" +
                            "- Inheritance\n" +
                            "- Interfaces\n\n" +
                            "🔗 [Java Official Docs](https://docs.oracle.com/javase/tutorial/)"
            );

            bookRepository.save(javaBook)
                    .doOnSuccess(saved -> System.out.println("✅ Book saved: " + saved.getName()))
                    .subscribe();
        };
    }

}
