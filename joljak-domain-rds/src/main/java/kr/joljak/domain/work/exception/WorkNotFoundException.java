package kr.joljak.domain.work.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WorkNotFoundException extends RuntimeException{

  public WorkNotFoundException(Long id){
    super("There is no work with that id : " + id);
  }
}
