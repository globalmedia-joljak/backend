package kr.joljak.domain.work.exception;

public class WorkNotFounException extends RuntimeException{
  public WorkNotFounException(Long id){
    super("There is no work with that id : " + id);
  }
}
