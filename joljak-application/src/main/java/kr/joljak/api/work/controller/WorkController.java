package kr.joljak.api.work.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import kr.joljak.api.work.request.WorkRequest;
import kr.joljak.api.work.response.WorkResponse;
import kr.joljak.domain.work.dto.SimpleWork;
import kr.joljak.domain.work.entity.Work;
import kr.joljak.domain.work.service.WorkService;
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
@RequestMapping("/api/v1/works")
public class WorkController {

  private final WorkService workService;

  @ApiOperation("팀 목록 게시판 생성 API")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public WorkResponse create(
    @RequestPart WorkRequest workRequest,
    @RequestPart(required = false) List<MultipartFile> images
  ) {
    SimpleWork simpleWork = WorkRequest.toDomainTeamRequest(workRequest);
    Work work = workService.addTeam(simpleWork, images);
    return WorkResponse.of(work);
  }

}
