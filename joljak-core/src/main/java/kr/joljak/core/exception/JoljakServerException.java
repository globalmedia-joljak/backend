package kr.joljak.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class JoljakServerException extends ResponseStatusException {
  public JoljakServerException() {
    this("joljak server exeption!");
  }

  public JoljakServerException(String message) {
    super(HttpStatus.INTERNAL_SERVER_ERROR, message);
  }
}
