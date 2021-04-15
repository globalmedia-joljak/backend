package kr.joljak.api.user.profile;

import static org.junit.Assert.assertEquals;

import kr.joljak.api.common.CommonApiTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class GetProfilesByNameAndCategoryAndProjectRole extends CommonApiTest {
  
  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getProfilesByNameAndCategoryAndProjectRole_Success() throws Exception {
  
    MvcResult mvcResult = mockMvc.perform(
      get(PROFILE_URL + "/search" + "?classOf=asd&name=asd&page=0&role=DEVELOPER&size=10")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();
    
    assertEquals(200, mvcResult.getResponse().getStatus());
  }
  
  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getProfilesByNameAndCategoryAndProjectRole_Success_OnlyByName() throws Exception {
    
    MvcResult mvcResult = mockMvc.perform(
      get(PROFILE_URL + "/search" + "?name=asd")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();
    
    assertEquals(200, mvcResult.getResponse().getStatus());
  }
  
}
