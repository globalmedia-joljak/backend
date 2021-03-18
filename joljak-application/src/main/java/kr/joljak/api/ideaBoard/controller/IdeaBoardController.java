package kr.joljak.api.ideaBoard.controller;

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import kr.joljak.api.ideaBoard.request.IdeaBoardRequest;
import kr.joljak.api.ideaBoard.response.IdeaBoardResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IdeaBoardController {

  @ApiOperation("아이디어 게시판 생성 API")
  @GetMapping
  @ResponseStatus(HttpStatus.CREATED)
  public IdeaBoardResponse create(@Valid @RequestBody IdeaBoardRequest ideaBoardRequest) {

    ideaBoardService.addIdeaBoard();

    return null;
  }

}
