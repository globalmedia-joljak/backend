package kr.joljak.api.notice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import kr.joljak.api.common.CommonApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class GetNoticeByPages extends CommonApiTest {

  @Test
  @WithMockUser(username = "testUser1", roles = "User")
  public void get_Notice_By_Pages() throws Exception {

    MvcResult mvcResult = mockMvc.perform(
      get(NOTICE_URL + "?page=0&size=10")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    assertEquals(200, mvcResult.getResponse().getStatus());
  }
}