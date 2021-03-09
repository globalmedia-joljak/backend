package kr.joljak.api.notice.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import kr.joljak.api.notice.request.NoticeRequest;
import kr.joljak.api.notice.response.NoticeResponse;
import kr.joljak.api.notice.response.NoticesResponse;
import kr.joljak.api.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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

  @ApiOperation("공지사항 생성 API")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public NoticeResponse create(@Valid @RequestBody NoticeRequest noticeRequest) {
    return noticeService.addNotice(noticeRequest);
  }

  @ApiOperation("공지사항 조회 API")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public NoticesResponse getNotices(@RequestParam int page,
    @RequestParam(defaultValue = "10") int size) {
    return noticeService.getNoticesByPage(page, size);
  }

  @ApiOperation("공지사항 개별 조회 API")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public NoticeResponse getNotice(@PathVariable("id") Long id) {
    return noticeService.getNoticeById(id);
  }

}
