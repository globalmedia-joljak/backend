package kr.joljak.domain.upload.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotMatchingFIleNameException extends ResponseStatusException {

  public NotMatchingFIleNameException() {
    this("file name is not match.");
  }

  public NotMatchingFIleNameException(String message) {
    super(HttpStatus.FORBIDDEN, message);
  }
}