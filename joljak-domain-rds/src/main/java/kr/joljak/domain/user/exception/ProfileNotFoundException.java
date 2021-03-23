package kr.joljak.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProfileNotFoundException extends ResponseStatusException {

  public ProfileNotFoundException() {
    this("not found profile.");
  }

  public ProfileNotFoundException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }
}

