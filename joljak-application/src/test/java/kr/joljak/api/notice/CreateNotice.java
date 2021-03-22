package kr.joljak.api.notice;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.joljak.api.common.CommonApiTest;
import kr.joljak.api.notice.request.NoticeRequest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class CreateNotice extends CommonApiTest {

  @Test
  @WithMockUser(username = "testUser1", roles = "USER")
  public void create_Success() throws Exception {
    // given
    NoticeRequest noticeRequest = createNoticeRequest("testUser1", "test", "test");
    String request = new ObjectMapper().writeValueAsString(noticeRequest);

    // when
    MvcResult mvcResult = mockMvc.perform(
      post(NOTICE_URL)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(request)
    ).andReturn();

    // then
    assertEquals(201, mvcResult.getResponse().getStatus());
  }

  @Test
  @WithMockUser(username = "testUser1", roles = "USER")
  public void create_Fail_UserDoesNotMatch() throws Exception {
    // given
    NoticeRequest noticeRequest = createNoticeRequest("testUser2", "test", "test");
    String request = new ObjectMapper().writeValueAsString(noticeRequest);

    // when
    MvcResult mvcResult = mockMvc.perform(
      post(NOTICE_URL)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(request)
    ).andReturn();

    // then
    assertEquals(403, mvcResult.getResponse().getStatus());

  }

}