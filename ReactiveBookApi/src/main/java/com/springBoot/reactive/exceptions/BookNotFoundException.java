package com.springBoot.reactive.exceptions;


public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message)
    {
        super (message);
    }


}
