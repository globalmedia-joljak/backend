package kr.joljak.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidTokenException extends ResponseStatusException {

  public InvalidTokenException() {
    this("invalid token exception");
  }

  public InvalidTokenException(String message) {
    super(HttpStatus.UNAUTHORIZED, message);
  }
}
