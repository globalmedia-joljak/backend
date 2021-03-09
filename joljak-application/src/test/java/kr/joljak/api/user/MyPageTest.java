package kr.joljak.api.user;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import kr.joljak.api.common.CommonApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class MyPageTest extends CommonApiTest {

  @Test
  @WithMockUser(username = "testUser1", roles = "USER")
  public void myPage_Success() throws Exception {
    //given, when
    MvcResult mvcResult = mockMvc.perform(
      get(USER_URL + "/" + TEST_USER_CLASS_OF)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    //then
    assertEquals(200, mvcResult.getResponse().getStatus());
  }

  @Test
  @WithMockUser(username = "testUser1", roles = "USER")
  public void myPage_Fail_UserDoesNotMatch() throws Exception {
    //given, when
    MvcResult mvcResult = mockMvc.perform(
      get(USER_URL + "/" + TEST_ADMIN_CLASS_OF)
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    //then
    assertEquals(403, mvcResult.getResponse().getStatus());
  }

  @Test
  @WithMockUser(username = "notFoundUser", roles = "USER")
  public void myPage_Fail_NotFoundUser() throws Exception {
    //given, when
    MvcResult mvcResult = mockMvc.perform(
      get(USER_URL + "/" + "notFoundUser")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
    ).andReturn();

    //then
    assertEquals(404, mvcResult.getResponse().getStatus());
  }
}
