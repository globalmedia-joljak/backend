package kr.joljak.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyClassOfExistException extends ResponseStatusException {

  public AlreadyClassOfExistException() {
    this("already classOf is exist.");
  }

  public AlreadyClassOfExistException(String message) {
    super(HttpStatus.CONFLICT, message);
  }
}

