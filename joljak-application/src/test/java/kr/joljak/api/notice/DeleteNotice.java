package kr.joljak.api.notice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import kr.joljak.api.common.CommonApiTest;
import kr.joljak.api.notice.request.NoticeRequest;
import kr.joljak.domain.notice.entity.Notice;
import kr.joljak.domain.notice.service.NoticeService;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class DeleteNotice extends CommonApiTest {

  @Autowired
  private UserService userService;
  @Autowired
  private NoticeService noticeService;

  private Long id;
  private Notice notice;
  private User user;
  private NoticeRequest noticeRequest;

  @Before
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "User")
  public void deleteNotice_setup() {
    //given
    noticeRequest = createNoticeRequest(TEST_USER_CLASS_OF, "test", "test");

    user = userService.getUserByClassOf(TEST_USER_CLASS_OF);
    notice = noticeService.addNotice(noticeRequest.toNotice(user));

    id = notice.getId();
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "User")
  public void deleteNotice_Success() throws Exception {

    MvcResult mvcResult = mockMvc.perform(
      delete(NOTICE_URL + "/" + id)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    assertEquals(200, mvcResult.getResponse().getStatus());

  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "User")
  public void deleteNotice_Fail_NoticeNotFoundException() throws Exception {

    MvcResult mvcResult = mockMvc.perform(
      delete(NOTICE_URL + "/" + id + 1)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    assertEquals(404, mvcResult.getResponse().getStatus());
  }

}
