package com.blog.app.exception;

public class ErrorMessage {

  public String code;
  public String message;
  public String date;

  public ErrorMessage(String code, String message, String date) {
    this.code = code;
    this.message = message;
    this.date = date;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public String getDate() {
    return date;
  }
}
