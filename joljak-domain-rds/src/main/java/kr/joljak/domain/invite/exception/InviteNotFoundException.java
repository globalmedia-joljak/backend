package kr.joljak.domain.invite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InviteNotFoundException extends ResponseStatusException {

  public InviteNotFoundException() {
    this("invite not found exception");
  }

  public InviteNotFoundException(String message) {
    super(HttpStatus.NOT_FOUND, message);
  }
}
