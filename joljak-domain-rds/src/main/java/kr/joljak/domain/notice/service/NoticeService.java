package kr.joljak.domain.notice.service;


import java.util.List;
import kr.joljak.domain.notice.dto.SimpleNoticeRequest;
import kr.joljak.domain.notice.entity.Notice;
import kr.joljak.domain.notice.exception.NoticeNotFoundException;
import kr.joljak.domain.notice.repository.NoticeRepository;
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
  public Notice addNotice(Notice notice) {
    userService.validAuthenticationClassOf(notice.getClassOf());
    return noticeRepository.save(notice);

  }

  @Transactional(readOnly = true)
  public List<Notice> getNoticesByPage(int page, int size) {

    Pageable pageable = FetchPages.of(page, size);
    return noticeRepository.findAll(pageable).getContent();
  }

  @Transactional
  public Notice updateNotice(Long id, SimpleNoticeRequest simpleNoticeRequest) {
    Notice notice = getNoticeById(id);
    userService.validAuthenticationClassOf(simpleNoticeRequest.getClassOf());
    notice.setTitle(simpleNoticeRequest.getTitle());
    notice.setContent(simpleNoticeRequest.getContent());
    return notice;
  }

  @Transactional
  public void deleteNotice(Long id) {
    Notice notice = getNoticeById(id);
    userService.validAuthenticationClassOf(notice.getClassOf());
    noticeRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
  public Notice getNoticeById(Long id) {
    return noticeRepository.findById(id)
      .orElseThrow(() -> new NoticeNotFoundException());
  }

}
