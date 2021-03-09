package kr.joljak.api.notice.service;


import java.util.List;
import java.util.stream.Collectors;
import kr.joljak.api.notice.request.NoticeRequest;
import kr.joljak.api.notice.response.NoticeResponse;
import kr.joljak.domain.notice.dto.SimpleNotice;
import kr.joljak.domain.notice.entity.Notice;
import kr.joljak.domain.notice.repository.NoticeRepository;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NoticeService {

  private final NoticeRepository noticeRepository;
  private final UserService userService;

  @Transactional
  public NoticeResponse addNotice(NoticeRequest noticeRequest) {
    userService.validAuthenticationClassOf(noticeRequest.getClassOf());
    User user = userService.getUserByClassOf(noticeRequest.getClassOf());
    Notice notice = noticeRepository.save(noticeRequest.to(user));

    return getNoticeResponse(SimpleNotice.of(notice));
  }

  private NoticeResponse getNoticeResponse(SimpleNotice simpleNotice) {
    return NoticeResponse.builder()
      .classOf(simpleNotice.getClassOf())
      .title(simpleNotice.getTitle())
      .content(simpleNotice.getContent())
      .createdDate(simpleNotice.getCreatedDate())
      .modifiedDate(simpleNotice.getModifiedDate())
      .build();
  }

  public List<NoticeResponse> getAll() {
    List<Notice> noticeList = noticeRepository.findAll();
    return noticeList.stream()
      .map(notice -> getNoticeResponse(SimpleNotice.of(notice)))
      .collect(Collectors.toList());
  }
}
