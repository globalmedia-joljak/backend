package kr.joljak.api.notice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import kr.joljak.api.common.CommonApiTest;
import kr.joljak.api.notice.request.NoticeRequest;
import kr.joljak.api.notice.response.NoticeResponse;
import kr.joljak.api.notice.service.NoticeService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class GetNotices extends CommonApiTest {

  @Autowired
  private NoticeService noticeService;

  @Test
  @WithMockUser(username = "testUser1", roles = "User")
  public void get_Notice_By_Pages() throws Exception {

    MvcResult mvcResult = mockMvc.perform(
      get(NOTICE_URL + "?page=0&size=10")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    assertEquals(200, mvcResult.getResponse().getStatus());
  }

  @Test
  @WithMockUser(username = "testUser1", roles = "User")
  public void get_Notice_By_id_Success() throws Exception {

    NoticeRequest noticeRequest = createNoticeRequest("testUser1", "test", "test");

    NoticeResponse noticeResponse = noticeService.addNotice(noticeRequest);
    Long id = noticeResponse.getId();

    MvcResult mvcResult = mockMvc.perform(
      get(NOTICE_URL + "/" + id)
        .contentType(MediaType.APPLICATION_JSON_VALUE)

    ).andReturn();

    assertEquals(200, mvcResult.getResponse().getStatus());
  }

  @Test
  @WithMockUser(username = "testUser1", roles = "User")
  public void get_Notice_By_id_Fail() throws Exception {

    NoticeRequest noticeRequest = createNoticeRequest("testUser1", "test", "test");

    NoticeResponse noticeResponse = noticeService.addNotice(noticeRequest);
    Long id = noticeResponse.getId();

    MvcResult mvcResult = mockMvc.perform(
      get(NOTICE_URL + "/" + id + 1)
        .contentType(MediaType.APPLICATION_JSON_VALUE)

    ).andReturn();

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