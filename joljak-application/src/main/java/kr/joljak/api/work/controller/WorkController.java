package kr.joljak.api.work.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.joljak.api.work.request.RegisterWorkRequest;
import kr.joljak.api.work.request.UpdateWorkRequest;
import kr.joljak.api.work.response.WorkResponse;
import kr.joljak.api.work.response.WorksResponse;
import kr.joljak.core.security.AuthenticationUtils;
import kr.joljak.domain.util.FetchPages;
import kr.joljak.domain.work.dto.SimpleWork;
import kr.joljak.domain.work.dto.UpdateWork;
import kr.joljak.domain.work.entity.ProjectCategory;
import kr.joljak.domain.work.entity.Work;
import kr.joljak.domain.work.service.WorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/works")
public class WorkController {
  
  private final WorkService workService;
  
  @ApiOperation("작품 게시판 생성 API")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public WorkResponse create(
    @Valid RegisterWorkRequest registerWorkRequest
  ) {
    log.info( "]-----] WorkController::create [-----[ classOf : {}", AuthenticationUtils.getClassOf());

    SimpleWork simpleWork = RegisterWorkRequest.toDomainWorkRequest(registerWorkRequest);
    Work work = workService.addWork(simpleWork);
    return WorkResponse.of(work);
  }
  
  @ApiOperation("작품 게시판 조회 API")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public WorksResponse getWorks(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ) {
    log.info( "]-----] WorkController::getWorks [-----[ page : {}, size : {}", page, size);

    Page<WorkResponse> workPage = workService.getWorksByPage(FetchPages.of(page, size))
      .map(WorkResponse::of);
    
    return WorksResponse.builder()
      .workResponseList(workPage)
      .build();
  }
  
  @ApiOperation("작품 게시판 개별 조회 API")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public WorkResponse getWorkById(
    @PathVariable Long id
  ) {
    log.info( "]-----] WorkController::getWorkById [-----[ id : {},", id);

    Work work = workService.getWorkById(id);
    return WorkResponse.of(work);
  }
  
  @ApiOperation("작품 게시판 수정 API")
  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public WorkResponse updateWork(
    @PathVariable Long id,
    UpdateWorkRequest updateWorkRequest
  ) {
    log.info( "]-----] WorkController::updateWork [-----[ id : {}, classOf : {}", id, AuthenticationUtils.getClassOf());

    UpdateWork updateWork = UpdateWorkRequest.toUpdateWork(updateWorkRequest);
    Work work = workService.updateWorkById(id, updateWork);
    return WorkResponse.of(work);
  }
  
  @ApiOperation("작품 게시판 삭제 API")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteWork(
    @PathVariable Long id
  ) {
    log.info( "]-----] WorkController::deleteWork [-----[ id : {}, classOf : {}", id, AuthenticationUtils.getClassOf());

    workService.deleteWorkById(id);
  }
  
  @ApiOperation("연도 리스트 조회")
  @GetMapping("/years")
  @ResponseStatus(HttpStatus.OK)
  public List<String> getYears() {

    return workService.getExhibitedYear();
  }
  
  @ApiOperation("작품 검색 API")
  @GetMapping("/search")
  @ResponseStatus(HttpStatus.OK)
  public WorksResponse searchWorks(
    @RequestParam(required = false) ProjectCategory category,
    @RequestParam(required = false) String exhibitedYear,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ) {
    log.info( "]-----] WorkController::searchWorks [-----[ category : {}, year : {}, page : {}, size : {}"
      , category, exhibitedYear, page, size);

    Page<WorkResponse> workPage = workService
      .getWorkByExhibitedYearAndCategory(category, exhibitedYear, FetchPages.of(page, size))
      .map(WorkResponse::of);
    
    return WorksResponse.builder()
      .workResponseList(workPage)
      .build();
  }
  
}
