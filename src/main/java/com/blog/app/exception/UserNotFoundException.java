package com.blog.app.exception;

public class UserNotFoundException extends RuntimeException {

  private String code;

  public UserNotFoundException(String code, String message) {
    super(message);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
