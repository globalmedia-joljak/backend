package kr.joljak.domain.upload.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotMatchingFileNameException extends ResponseStatusException {

  public NotMatchingFileNameException() {
    this("file name is not match.");
  }

  public NotMatchingFileNameException(String message) {
    super(HttpStatus.FORBIDDEN, message);
  }
}