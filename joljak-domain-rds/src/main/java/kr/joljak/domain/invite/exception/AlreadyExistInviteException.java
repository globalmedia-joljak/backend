package kr.joljak.domain.invite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyExistInviteException extends ResponseStatusException {

  public AlreadyExistInviteException() {
    this("already exist invite.");
  }

  public AlreadyExistInviteException(String message) {
    super(HttpStatus.CONFLICT, message);
  }
}
