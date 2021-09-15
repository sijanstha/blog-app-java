package com.blog.app.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(value = {PostNotFoundException.class})
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ErrorMessage handlePostNotFoundException(PostNotFoundException ex, WebRequest request) {
    return new ErrorMessage(
        ex.getCode(),
        ex.getMessage(),
        new Date().toString()
    );
  }

  @ExceptionHandler(value = {UserNotFoundException.class})
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ErrorMessage handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
    return new ErrorMessage(
        ex.getCode(),
        ex.getMessage(),
        new Date().toString()
    );
  }

  @ExceptionHandler(value = {BadRequestException.class})
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorMessage handleBadRequestException(BadRequestException ex, WebRequest request) {
    return new ErrorMessage(
        ex.getCode(),
        ex.getMessage(),
        new Date().toString()
    );
  }
}
