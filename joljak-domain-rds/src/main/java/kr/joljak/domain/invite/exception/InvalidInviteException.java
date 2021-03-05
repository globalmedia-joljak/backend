package kr.joljak.domain.invite.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidInviteException extends ResponseStatusException {

  public InvalidInviteException() {
    this("invalid invite exception");
  }

  public InvalidInviteException(String message) {
    super(HttpStatus.UNAUTHORIZED, message);
  }
}
