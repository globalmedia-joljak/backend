package kr.joljak.domain.team.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TeamNotFoundException extends ResponseStatusException {
  
  public TeamNotFoundException() { this("not found team."); }
  
  public TeamNotFoundException(String message) { super(HttpStatus.NOT_FOUND, message); }
}
