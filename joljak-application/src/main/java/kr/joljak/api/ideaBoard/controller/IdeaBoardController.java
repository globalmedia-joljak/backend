package kr.joljak.api.ideaBoard.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import kr.joljak.api.ideaBoard.request.IdeaBoardRequest;
import kr.joljak.api.ideaBoard.response.IdeaBoardResponse;
import kr.joljak.api.ideaBoard.response.IdeaBoardsResponse;
import kr.joljak.domain.IdeaBoard.dto.SimpleIdeaBoard;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard;
import kr.joljak.domain.IdeaBoard.service.IdeaBoardService;
import kr.joljak.domain.util.FetchPages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @ApiOperation("아이디어 게시판 조회 API")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public IdeaBoardsResponse getAllIdeaBoard(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ){
    List<IdeaBoard> ideaBoardList = ideaBoardService.getIdeaBoardsByPage(page, size);
    List<IdeaBoardResponse> ideaBoardResponseList = getIdeaBoardResponseListFrom(ideaBoardList);

    return IdeaBoardsResponse.builder()
      .ideaBoardResponseList(ideaBoardResponseList)
      .page(FetchPages.of(page, size))
      .build();
  }

  private List<IdeaBoardResponse> getIdeaBoardResponseListFrom(List<IdeaBoard> ideaBoardList){
    return ideaBoardList.stream()
      .map(ideaBoard -> IdeaBoardResponse.of(ideaBoard))
      .collect(Collectors.toList());
  }

}