package kr.joljak.domain.ideaboard.exception;

public class IdeaBoardNotFoundException extends RuntimeException {

  public IdeaBoardNotFoundException(Long id) {
    super("There is no Notice with that Ideaboard_id : " + id);
  }

}
