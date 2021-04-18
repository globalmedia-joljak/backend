package kr.joljak.api.team;

import kr.joljak.api.common.CommonApiTest;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class GetTeamsByCategory extends CommonApiTest {
  
  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getTeamsByCategory_Success() throws Exception {
    // when, given
    MvcResult mvcResult = mockMvc.perform(
      get(TEAM_URL + "/search" + "?category=WEB_APP&page=0&size=10")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();
    
    // then
    assertEquals(200, mvcResult.getResponse().getStatus());
  }
  
  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getTeamByCategory_Success_OnlyByPageAndSize() throws Exception {
    // when, given
    MvcResult mvcResult = mockMvc.perform(
      get(TEAM_URL + "/search" + "?page=0&size=10")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();
    
    // then
    assertEquals(200, mvcResult.getResponse().getStatus());
  }
  
}
