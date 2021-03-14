package kr.joljak.domain.notice;

import java.util.List;
import kr.joljak.core.jwt.PermissionException;
import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.notice.dto.SimpleNoticeRequest;
import kr.joljak.domain.notice.entity.Notice;
import kr.joljak.domain.notice.exception.NoticeNotFoundException;
import kr.joljak.domain.notice.service.NoticeService;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.exception.UserNotFoundException;
import kr.joljak.domain.user.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

public class NoticeServiceTest extends CommonDomainTest {

  @Autowired
  private NoticeService noticeService;
  @Autowired
  private UserService userService;

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void addNotice_Success() {

    // given
    User user = userService.getUserByClassOf(TEST_USER_CLASS_OF);
    Notice notice = createNotice(user, "test", TEST_USER_CLASS_OF, "test content");

    // when
    Notice newNotice = noticeService.addNotice(notice);
  }

  @Test(expected = PermissionException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void addNotice_Fail_NotFoundUser() {

    // given
    User user = userService.getUserByClassOf(TEST_USER_CLASS_OF);
    Notice notice = createNotice(user, "test", TEST_USER_CLASS_OF + "2", "test content");

    // when
    Notice newNotice = noticeService.addNotice(notice);
  }

  @Test
  public void getNoticesByPage_Success() {

    // when
    List<Notice> noticeList = noticeService.getNoticesByPage(0, 10);
  }

  @Test
  public void getNoticesByPage_Success_SizeUnderOne() {

    // when
    List<Notice> noticeList = noticeService.getNoticesByPage(0, 0);
  }

  @Test
  public void getNoticesByPage_Success_PageUnderZero() {
    // when
    List<Notice> noticeList = noticeService.getNoticesByPage(-1, 10);
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void updateNotice_Success() {

    //given
    SimpleNoticeRequest simpleNoticeRequest = createSimpleNoticeRequest(TEST_USER_CLASS_OF,
      "test content", "test");

    User user = userService.getUserByClassOf(TEST_USER_CLASS_OF);
    Notice notice = createNotice(user, simpleNoticeRequest.getTitle(),
      simpleNoticeRequest.getClassOf(), simpleNoticeRequest.getContent());
    notice = noticeService.addNotice(notice);

    Long id = notice.getId();

    // when
    Notice updateNotice = noticeService.updateNotice(id, simpleNoticeRequest);
  }

  @Test(expected = UserNotFoundException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void updateNotice_Fail_UserDoesNotMatch() {

    //given
    SimpleNoticeRequest simpleNoticeRequest = createSimpleNoticeRequest(TEST_USER_CLASS_OF,
      "test content", "test");

    User user = userService.getUserByClassOf(TEST_USER_CLASS_OF + "2");
    Notice notice = createNotice(user, simpleNoticeRequest.getTitle(),
      simpleNoticeRequest.getClassOf(), simpleNoticeRequest.getContent());
    notice = noticeService.addNotice(notice);

    Long id = notice.getId();

    // when
    Notice updateNotice = noticeService.updateNotice(id, simpleNoticeRequest);
  }

  @Test(expected = NoticeNotFoundException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void updateNotice_Fail_NoticeNotFoundException() {

    //given
    SimpleNoticeRequest simpleNoticeRequest = createSimpleNoticeRequest(TEST_USER_CLASS_OF,
      "test content", "test");

    User user = userService.getUserByClassOf(TEST_USER_CLASS_OF);
    Notice notice = createNotice(user, simpleNoticeRequest.getTitle(),
      simpleNoticeRequest.getClassOf(), simpleNoticeRequest.getContent());
    notice = noticeService.addNotice(notice);

    Long id = notice.getId();

    // when
    Notice updateNotice = noticeService.updateNotice(id + 1, simpleNoticeRequest);
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getNoticeById_Success() {

    //given
    SimpleNoticeRequest simpleNoticeRequest = createSimpleNoticeRequest(TEST_USER_CLASS_OF,
      "test content", "test");

    User user = userService.getUserByClassOf(TEST_USER_CLASS_OF);
    Notice notice = createNotice(user, simpleNoticeRequest.getTitle(),
      simpleNoticeRequest.getClassOf(), simpleNoticeRequest.getContent());
    notice = noticeService.addNotice(notice);

    Long id = notice.getId();

    // when
    Notice getNotice = noticeService.getNoticeById(id);
  }

  @Test(expected = NoticeNotFoundException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getNoticeById_Fail_NoticeNotFoundException() {

    //given
    SimpleNoticeRequest simpleNoticeRequest = createSimpleNoticeRequest(TEST_USER_CLASS_OF,
      "test content", "test");

    User user = userService.getUserByClassOf(TEST_USER_CLASS_OF);
    Notice notice = createNotice(user, simpleNoticeRequest.getTitle(),
      simpleNoticeRequest.getClassOf(), simpleNoticeRequest.getContent());
    notice = noticeService.addNotice(notice);

    Long id = notice.getId();

    // when
    Notice getNotice = noticeService.getNoticeById(id + 1);
  }

  private SimpleNoticeRequest createSimpleNoticeRequest(String classOf, String content,
    String title) {
    return SimpleNoticeRequest.builder()
      .classOf(classOf)
      .content(content)
      .title(title)
      .build();

  }


  public Notice createNotice(User user, String title, String classOf, String content) {
    return Notice.builder()
      .user(user)
      .title(title)
      .classOf(classOf)
      .content(content)
      .build();
  }
}