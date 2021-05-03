package kr.joljak.api.ideaboard.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import kr.joljak.api.ideaboard.request.IdeaBoardRequest;
import kr.joljak.api.ideaboard.response.IdeaBoardResponse;
import kr.joljak.api.ideaboard.response.IdeaBoardsResponse;
import kr.joljak.domain.ideaboard.dto.SimpleIdeaBoard;
import kr.joljak.domain.ideaboard.entity.IdeaBoard;
import kr.joljak.domain.ideaboard.service.IdeaBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/ideaboards")
public class IdeaBoardController {
  
  private final IdeaBoardService ideaBoardService;
  
  @ApiOperation("아이디어 게시판 생성 API")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public IdeaBoardResponse create(
    @Valid IdeaBoardRequest ideaBoardRequest
  ) {
    SimpleIdeaBoard simpleIdeaBoard = ideaBoardRequest
      .toDomainIdeaBoardRequest(ideaBoardRequest);
    IdeaBoard ideaBoard = ideaBoardService.addIdeaBoard(simpleIdeaBoard);
    
    return IdeaBoardResponse.of(ideaBoard);
  }
  
  @ApiOperation("아이디어 게시판 조회 API")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public IdeaBoardsResponse getAllIdeaBoard(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ) {
    Page<IdeaBoardResponse> ideaBoardResponsePage = ideaBoardService.getIdeaBoardsByPage(page, size)
      .map(IdeaBoardResponse::of);
    
    return IdeaBoardsResponse.builder()
      .ideaBoardResponseList(ideaBoardResponsePage)
      .build();
  }
  
  @ApiOperation("아이디어 게시판 개별 조회 API")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public IdeaBoardResponse getIdeaBoard(
    @PathVariable("id") Long id
  ) {
    IdeaBoard ideaBoard = ideaBoardService.getIdeaBoardsById(id);
    return IdeaBoardResponse.of(ideaBoard);
  }
  
  @ApiOperation("아이디어 게시판 업데이트 API")
  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public IdeaBoardResponse updateIdeaBoard(
    @PathVariable Long id,
    @Valid IdeaBoardRequest ideaBoardRequest
  ) {
    SimpleIdeaBoard simpleIdeaBoard = ideaBoardRequest
      .toDomainIdeaBoardRequest(ideaBoardRequest);
    IdeaBoard ideaBoard = ideaBoardService.updateIdeaBoardById(id, simpleIdeaBoard);
    return IdeaBoardResponse.of(ideaBoard);
  }
  
  @ApiOperation("아이디어 게시판 삭제 API")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteIdeaBoard(
    @PathVariable Long id
  ) {
    ideaBoardService.deleteIdeaBoardById(id);
  }
  
  private List<IdeaBoardResponse> getIdeaBoardResponseListFrom(List<IdeaBoard> ideaBoardList) {
    return ideaBoardList.stream()
      .map(ideaBoard -> IdeaBoardResponse.of(ideaBoard))
      .collect(Collectors.toList());
  }
  
}