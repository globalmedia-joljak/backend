package kr.joljak.api.notice.service;



import java.util.List;
import java.util.stream.Collectors;
import kr.joljak.api.notice.request.NoticeRequest;
import kr.joljak.api.notice.response.NoticeResponse;
import kr.joljak.api.notice.response.NoticesResponse;
import kr.joljak.domain.notice.dto.SimpleNotice;
import kr.joljak.domain.notice.entity.Notice;
import kr.joljak.domain.notice.exception.NoticeNotFoundException;
import kr.joljak.domain.notice.repository.NoticeRepository;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.service.UserService;
import kr.joljak.domain.util.FetchPages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
      .id(simpleNotice.getId())
      .classOf(simpleNotice.getClassOf())
      .author(simpleNotice.getAuthor())
      .title(simpleNotice.getTitle())
      .content(simpleNotice.getContent())
      .createdDate(simpleNotice.getCreatedDate())
      .modifiedDate(simpleNotice.getModifiedDate())
      .build();
  }

  @Transactional(readOnly = true)
  public NoticesResponse getNoticesByPage(int page, int size) {

    Pageable pageable = FetchPages.of(page, size);
    List<Notice> noticeList = noticeRepository.findAll(pageable).getContent();

    List<NoticeResponse> noticeResponseList = noticeList.stream()
      .map(notice -> getNoticeResponse(SimpleNotice.of(notice)))
      .collect(Collectors.toList());

    return NoticesResponse.builder()
      .noticeResponseList(noticeResponseList)
      .page(pageable)
      .build();
  }

  @Transactional(readOnly = true)
  public NoticeResponse getNoticeById(Long id) {
    Notice notice = noticeRepository.findById(id)
      .orElseThrow(() -> new NoticeNotFoundException());
    return getNoticeResponse(SimpleNotice.of(notice));
  }
}
