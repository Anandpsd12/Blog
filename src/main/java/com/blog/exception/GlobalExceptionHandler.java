package com.blog.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.ResponseEntity;
import com.blog.payload.ErrorDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice      // Any exception occurs in the project will come here, Acts like a catch BLock
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class) // First it go to the Class then it come to this Method
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(


            ResourceNotFoundException exception,  // It supress the Exception , Acts like Catch Block


            WebRequest webRequest){


        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);

    }

    // global exceptions
    @ExceptionHandler(Exception.class)     // If we puttting some Dublicate values in DB / EXCEPTION CLASS can handle any EXCEPTIONS
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception,
                                                              WebRequest webRequest){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}




