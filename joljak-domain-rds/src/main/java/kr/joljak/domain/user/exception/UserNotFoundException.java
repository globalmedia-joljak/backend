package kr.joljak.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException extends ResponseStatusException {

  public UserNotFoundException() {
    this("not found user.");
  }

  public UserNotFoundException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }
}

