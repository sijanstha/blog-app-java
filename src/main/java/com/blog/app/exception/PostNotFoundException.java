package com.blog.app.exception;

public class PostNotFoundException extends RuntimeException {

  private String code;

  public PostNotFoundException(String code, String message) {
    super(message);
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
