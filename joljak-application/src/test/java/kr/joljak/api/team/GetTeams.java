package kr.joljak.api.team;

import kr.joljak.api.common.CommonApiTest;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class GetTeams extends CommonApiTest {
  
  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getTeams_Success() throws Exception {
  
    MvcResult mvcResult = mockMvc.perform(
      get(TEAM_URL + "?page=0&size=10")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();
    
    assertEquals(200, mvcResult.getResponse().getStatus());
  }
  
  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getTeams_Success_SizeAndPageUnderZero() throws Exception {
    
    MvcResult mvcResult = mockMvc.perform(
      get(TEAM_URL + "?page=-1&size=-1")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();
    
    assertEquals(200, mvcResult.getResponse().getStatus());
  }
  
}
