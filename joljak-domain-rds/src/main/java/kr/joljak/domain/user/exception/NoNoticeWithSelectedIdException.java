package kr.joljak.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoNoticeWithSelectedIdException extends RuntimeException {

  public NoNoticeWithSelectedIdException() {
    super("There is no Notice with that Notice_id");
  }
}
