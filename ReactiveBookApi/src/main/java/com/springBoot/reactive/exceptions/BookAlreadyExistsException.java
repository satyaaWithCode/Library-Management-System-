package com.springBoot.reactive.exceptions;

public class BookAlreadyExistsException extends RuntimeException{

    public BookAlreadyExistsException(String message)
    {
        super(message);
    }

}
