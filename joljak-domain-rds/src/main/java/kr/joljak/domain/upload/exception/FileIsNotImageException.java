package kr.joljak.domain.upload.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FileIsNotImageException extends ResponseStatusException {

  public FileIsNotImageException() {
    this("file is not image exception");
  }

  public FileIsNotImageException(String message) {
    super(HttpStatus.FORBIDDEN, message);
  }
}
