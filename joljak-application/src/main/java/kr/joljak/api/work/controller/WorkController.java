package kr.joljak.api.work.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import kr.joljak.api.work.request.RegisterWorkRequest;
import kr.joljak.api.work.request.UpdateWorkRequest;
import kr.joljak.api.work.response.WorkResponse;
import kr.joljak.api.work.response.WorksResponse;
import kr.joljak.domain.util.FetchPages;
import kr.joljak.domain.work.dto.SimpleWork;
import kr.joljak.domain.work.dto.UpdateWork;
import kr.joljak.domain.work.entity.ProjectCategory;
import kr.joljak.domain.work.entity.Work;
import kr.joljak.domain.work.service.WorkService;
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
@RequestMapping("/api/v1/works")
public class WorkController {
  
  private final WorkService workService;
  
  @ApiOperation("작품 게시판 생성 API")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public WorkResponse create(
    @Valid RegisterWorkRequest registerWorkRequest
  ) {
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
    Page<Work> workPage = workService.getWorksByPage(FetchPages.of(page, size));
    
    List<WorkResponse> workResponseList = getWorkResponseListFrom(workPage.getContent());
    
    return WorksResponse.builder()
      .workResponseList(workResponseList)
      .page(workPage.getPageable())
      .build();
  }
  
  @ApiOperation("작품 게시판 개별 조회 API")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public WorkResponse getWorkById(
    @PathVariable Long id
  ) {
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
    workService.deleteWorkById(id);
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
    Page<Work> workPage = workService.getWorkByExhibitedYearAndCategory(category, exhibitedYear, FetchPages.of(page, size));
    List<WorkResponse> workResponseList = getWorkResponseListFrom(workPage.getContent());

    return WorksResponse.builder()
      .workResponseList(workResponseList)
      .page(workPage.getPageable())
      .build();
  }
  
  private List<WorkResponse> getWorkResponseListFrom(List<Work> workList) {
    return workList.stream()
      .map(work -> WorkResponse.of(work))
      .collect(Collectors.toList());
  }
  
}
