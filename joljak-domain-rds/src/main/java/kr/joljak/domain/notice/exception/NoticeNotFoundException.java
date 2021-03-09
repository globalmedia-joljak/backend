package kr.joljak.domain.notice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoticeNotFoundException extends RuntimeException {

  public NoticeNotFoundException() {
    super("There is no Notice with that Notice_id");
  }
}
