package kr.joljak.api.notice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import kr.joljak.api.common.CommonApiTest;
import kr.joljak.api.notice.request.NoticeRequest;
import kr.joljak.domain.notice.entity.Notice;
import kr.joljak.domain.notice.service.NoticeService;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class GetNotice extends CommonApiTest {

  @Autowired
  private NoticeService noticeService;

  @Autowired
  private UserService userService;

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "User")
  public void getNotice_Success() throws Exception {

    NoticeRequest noticeRequest = createNoticeRequest(TEST_USER_CLASS_OF, "test", "test");

    User user = userService.getUserByClassOf(noticeRequest.getClassOf());
    Notice notice = noticeService.addNotice(noticeRequest.toNotice(user));

    Long id = notice.getId();

    MvcResult mvcResult = mockMvc.perform(
      get(NOTICE_URL + "/" + id)
        .contentType(MediaType.APPLICATION_JSON_VALUE)

    ).andReturn();

    assertEquals(200, mvcResult.getResponse().getStatus());
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "User")
  public void getNotice_Fail_NoticeNotFoundException() throws Exception {

    NoticeRequest noticeRequest = createNoticeRequest(TEST_USER_CLASS_OF, "test", "test");

    User user = userService.getUserByClassOf(noticeRequest.getClassOf());
    Notice notice = noticeService.addNotice(noticeRequest.toNotice(user));

    Long id = notice.getId();

    MvcResult mvcResult = mockMvc.perform(
      get(NOTICE_URL + "/" + id + 1)
        .contentType(MediaType.APPLICATION_JSON_VALUE)

    ).andReturn();

    assertEquals(404, mvcResult.getResponse().getStatus());
  }


}