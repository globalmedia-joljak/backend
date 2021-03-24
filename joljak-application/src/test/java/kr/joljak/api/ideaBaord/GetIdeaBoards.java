package kr.joljak.api.ideaBaord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import kr.joljak.api.common.CommonApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class GetIdeaBoards extends CommonApiTest {

  @Test
  @WithMockUser(username = "testUser1", roles = "User")
  public void getIdeaBoards_Success() throws Exception {

    MvcResult mvcResult = mockMvc.perform(
      get(IDEABOARD_URL + "?page=0&size=10")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    assertEquals(200, mvcResult.getResponse().getStatus());
  }

  @Test
  @WithMockUser(username = "testUser1", roles = "User")
  public void getIdeaBoards_Success_SizeUnderOne() throws Exception {

    MvcResult mvcResult = mockMvc.perform(
      get(IDEABOARD_URL + "?page=0&size=-1")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    assertEquals(200, mvcResult.getResponse().getStatus());
  }

  @Test
  @WithMockUser(username = "testUser1", roles = "User")
  public void getIdeaBoards_Success_PageUnderZero() throws Exception {

    MvcResult mvcResult = mockMvc.perform(
      get(IDEABOARD_URL + "?page=-1&size=10")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    assertEquals(200, mvcResult.getResponse().getStatus());
  }

}
