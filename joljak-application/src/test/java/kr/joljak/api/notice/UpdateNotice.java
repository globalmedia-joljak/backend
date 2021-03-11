package kr.joljak.api.notice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import com.fasterxml.jackson.databind.ObjectMapper;
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

public class UpdateNotice extends CommonApiTest {

  @Autowired
  private UserService userService;
  @Autowired
  private NoticeService noticeService;

  @Test
  @WithMockUser(username = "testUser1", roles = "USER")
  public void updateNotice_Success() throws Exception {
    //given
    NoticeRequest noticeRequest = createNoticeRequest("testUser1", "test", "test");

    User user = userService.getUserByClassOf("testUser1");
    Notice notice = noticeService.addNotice(noticeRequest.toNotice(user));

    Long id = notice.getId();

    NoticeRequest newNoticeRequest = createNoticeRequest("testUser1", "test1", "test1");
    String request = new ObjectMapper().writeValueAsString(newNoticeRequest);

    //when
    MvcResult mvcResult = mockMvc.perform(
      patch(NOTICE_URL + "/" + id)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(request)
    ).andReturn();

    //then
    assertEquals(200, mvcResult.getResponse().getStatus());
  }

  @Test
  @WithMockUser(username = "testUser1", roles = "USER")
  public void updateNotice_Fail_UserDoesNotMatch() throws Exception {
    //given
    NoticeRequest noticeRequest = createNoticeRequest("testUser1", "test", "test");

    User user = userService.getUserByClassOf("testUser1");
    Notice notice = noticeService.addNotice(noticeRequest.toNotice(user));

    Long id = notice.getId();

    NoticeRequest newNoticeRequest = createNoticeRequest("testUser", "test1", "test1");
    String request = new ObjectMapper().writeValueAsString(newNoticeRequest);

    //when
    MvcResult mvcResult = mockMvc.perform(
      patch(NOTICE_URL + "/" + id)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(request)
    ).andReturn();

    //then
    assertEquals(403, mvcResult.getResponse().getStatus());
  }

  @Test
  @WithMockUser(username = "testUser1", roles = "USER")
  public void updateNotice_Fail_NoticeNotFoundException() throws Exception {

    //given
    NoticeRequest noticeRequest = createNoticeRequest("testUser1", "test", "test");

    User user = userService.getUserByClassOf("testUser1");
    Notice notice = noticeService.addNotice(noticeRequest.toNotice(user));

    Long id = notice.getId();

    NoticeRequest newNoticeRequest = createNoticeRequest("testUser", "test1", "test1");
    String request = new ObjectMapper().writeValueAsString(newNoticeRequest);

    //when
    MvcResult mvcResult = mockMvc.perform(
      patch(NOTICE_URL + "/" + id + 1)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(request)
    ).andReturn();

    //then
    assertEquals(404, mvcResult.getResponse().getStatus());

  }

  private NoticeRequest createNoticeRequest(String classOf, String title, String content) {
    return new NoticeRequest(
      classOf,
      title,
      content
    );
  }

}