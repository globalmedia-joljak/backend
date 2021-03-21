package kr.joljak.api.ideaBoard.controller;

import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import kr.joljak.api.ideaBoard.request.IdeaBoardRequest;
import kr.joljak.api.ideaBoard.response.IdeaBoardResponse;
import kr.joljak.domain.IdeaBoard.dto.SimpleIdeaBoard;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard;
import kr.joljak.domain.IdeaBoard.service.IdeaBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ideaboards")
public class IdeaBoardController {

  private final IdeaBoardService ideaBoardService;

  @ApiOperation("아이디어 게시판 생성 API")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public IdeaBoardResponse create(
    @RequestPart("file") MultipartFile file,
    @Valid @RequestPart("ideaBoardRequest") IdeaBoardRequest ideaBoardRequest
  ) {
    SimpleIdeaBoard simpleIdeaBoard = ideaBoardRequest
      .toDomainIdeaBoardRequest(ideaBoardRequest);
    IdeaBoard ideaBoard = ideaBoardService.addIdeaBoard(simpleIdeaBoard, file);

    return IdeaBoardResponse.of(ideaBoard);
  }

}