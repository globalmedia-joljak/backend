package kr.joljak.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPasswordException extends ResponseStatusException {

  public InvalidPasswordException() {
    this("invalid password");
  }

  public InvalidPasswordException(String message) {
    super(HttpStatus.UNAUTHORIZED, message);
  }
}


