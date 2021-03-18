package kr.joljak.domain.upload.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MediaNotFoundException extends ResponseStatusException {

  public MediaNotFoundException() {
    this("file is not image exception");
  }

  public MediaNotFoundException(String message) {
    super(HttpStatus.FORBIDDEN, message);
  }
}