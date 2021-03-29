package kr.joljak.api.work;

import kr.joljak.api.common.CommonApiTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class GetWorks extends CommonApiTest {

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getWorks_Success() throws Exception {

    MvcResult mvcResult = mockMvc.perform(
      get(WORK_URL + "?page=0&size=10")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    assertEquals(200, mvcResult.getResponse().getStatus());
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getWorks_Success_SizAndPageUnderZero() throws Exception {

    MvcResult mvcResult = mockMvc.perform(
      get(IDEABOARD_URL + "?page=-1&size=-1")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
  }

}
