package kr.joljak.api.work;


import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import kr.joljak.api.common.CommonApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class GetWorksByCategoryAndExhibitedYear extends CommonApiTest {
  
  
  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getWorksByCategoryAndExhibitedYear_Success() throws Exception {
    // when, given
    MvcResult mvcResult = mockMvc.perform(
      get(WORK_URL + "/search" + "?category=WEB_APP&page=0&size=10&exhibitedYear=2020")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();
    
    // then
    assertEquals(200, mvcResult.getResponse().getStatus());
  }
  
  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getWorksByCategoryAndExhibitedYear_Success_OnlyByCategory() throws Exception {
    // when, given
    MvcResult mvcResult = mockMvc.perform(
      get(WORK_URL + "/search" + "?category=WEB_APP&page=0&size=10")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();
    
    // then
    assertEquals(200, mvcResult.getResponse().getStatus());
  }
  
  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void getWorksByCategoryAndExhibitedYear_Success_OnlyPageAndSize() throws Exception {
    // when, given
    MvcResult mvcResult = mockMvc.perform(
      get(WORK_URL + "/search" + "?page=0&size=10")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();
    
    // then
    assertEquals(200, mvcResult.getResponse().getStatus());
  }
  
}
