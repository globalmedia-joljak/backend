package kr.joljak.api.notice.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import kr.joljak.api.notice.request.NoticeRequest;
import kr.joljak.api.notice.response.NoticeResponse;
import kr.joljak.api.notice.response.NoticesResponse;
import kr.joljak.domain.notice.dto.SimpleNotice;
import kr.joljak.domain.notice.entity.Notice;
import kr.joljak.domain.notice.service.NoticeService;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.service.UserService;
import kr.joljak.domain.util.FetchPages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notices")
public class NoticeController {

  private final NoticeService noticeService;
  private final UserService userService;

  @ApiOperation("공지사항 생성 API")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public NoticeResponse create(@Valid @RequestBody NoticeRequest noticeRequest) {
    User user = userService.getUserByClassOf(noticeRequest.getClassOf());
    Notice notice = noticeService.addNotice(noticeRequest.toNotice(user));
    return getNoticeResponse(SimpleNotice.of(notice));
  }

  @ApiOperation("공지사항 조회 API")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public NoticesResponse getNotices(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ) {
    List<Notice> noticeList = noticeService.getNoticesByPage(page, size);
    List<NoticeResponse> noticeResponseList = getNoticeResponseListFrom(noticeList);
    return NoticesResponse.builder()
      .noticeResponseList(noticeResponseList)
      .page(FetchPages.of(page, size))
      .build();
  }

  @ApiOperation("공지사항 개별 조회 API")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public NoticeResponse getNotice(@PathVariable("id") Long id) {

    Notice notice = noticeService.getNoticeById(id);
    return getNoticeResponse(SimpleNotice.of(notice));
  }

  @ApiOperation("공지사항 수정 API")
  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public NoticeResponse updateNotice(
    @PathVariable("id") Long id,
    @Valid @RequestBody NoticeRequest noticeRequest
  ) {
    Notice newNotice = noticeService.updateNotice(id, NoticeRequest.to(noticeRequest));
    return getNoticeResponse(SimpleNotice.of(newNotice));

  }

  @ApiOperation("공지사항 삭제 API")
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteNotice(
    @PathVariable("id") Long id
  ) {
    noticeService.deleteNotice(id);
  }


  private List<NoticeResponse> getNoticeResponseListFrom(List<Notice> noticeList) {
    return noticeList.stream()
      .map(notice -> getNoticeResponse(SimpleNotice.of(notice)))
      .collect(Collectors.toList());
  }

  private NoticeResponse getNoticeResponse(SimpleNotice simpleNotice) {
    return NoticeResponse.builder()
      .id(simpleNotice.getId())
      .classOf(simpleNotice.getClassOf())
      .author(simpleNotice.getAuthor())
      .title(simpleNotice.getTitle())
      .content(simpleNotice.getContent())
      .createdDate(simpleNotice.getCreatedDate())
      .modifiedDate(simpleNotice.getModifiedDate())
      .build();
  }

}