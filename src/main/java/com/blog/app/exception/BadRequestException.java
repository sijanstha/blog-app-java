package com.blog.app.exception;

public class BadRequestException extends RuntimeException {

  private String code;

  public BadRequestException(String code, String message) {
    super(message);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
