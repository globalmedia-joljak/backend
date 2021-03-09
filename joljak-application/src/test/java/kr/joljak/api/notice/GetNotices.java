package kr.joljak.api.notice;


import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import kr.joljak.api.common.CommonApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class GetNotices extends CommonApiTest {

  @Test
  @WithMockUser(username = "testUser1", roles = "USER")
  public void getAllNotices() throws Exception {

    MvcResult mvcResult = mockMvc.perform(
      get(NOTICE_URL)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    assertEquals(200, mvcResult.getResponse().getStatus());
  }

}