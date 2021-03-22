package kr.joljak.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyProfileExistException extends ResponseStatusException {

  public AlreadyProfileExistException() {
    this("already profile is exist.");
  }

  public AlreadyProfileExistException(String message) {
    super(HttpStatus.CONFLICT, message);
  }
}

