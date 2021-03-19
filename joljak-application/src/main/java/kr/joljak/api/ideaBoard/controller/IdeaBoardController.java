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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/IdeaBoards")
public class IdeaBoardController {

  private final IdeaBoardService ideaBoardService;

  @ApiOperation("아이디어 게시판 생성 API")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public IdeaBoardResponse create(
    @Valid @RequestBody IdeaBoardRequest ideaBoardRequest
  ) {
    SimpleIdeaBoard simpleIdeaBoard = ideaBoardRequest.toDomainIdeaBoardRequest(ideaBoardRequest);
    IdeaBoard ideaBoard = ideaBoardService.addIdeaBoard(simpleIdeaBoard);

    return getIdeaNoticeBoard(ideaBoard);
  }

  private IdeaBoardResponse getIdeaNoticeBoard(IdeaBoard ideaBoard) {
    return IdeaBoardResponse.builder()
      .title(ideaBoard.getTitle())
      .content(ideaBoard.getContent())
      .classOf(ideaBoard.getUser().getClassOf())
      .status(ideaBoard.getStatus())
      .contact(ideaBoard.getContact())
      .requiredPosition(ideaBoard.getRequiredPosiotion())
      .name(ideaBoard.getUser().getName())
      .mainProjectRole(ideaBoard.getUser().getMainProjectRole())
      .file(ideaBoard.getFileInfo())
      .image(ideaBoard.getImageInfo())
      .build();
  }
}
