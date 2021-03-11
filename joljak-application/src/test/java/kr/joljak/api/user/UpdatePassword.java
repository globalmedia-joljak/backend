package kr.joljak.api.user;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.joljak.api.common.CommonApiTest;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

public class UpdatePassword extends CommonApiTest {
  @Test
  @WithMockUser(username = "testUser1", roles = "USER")
  public void updatePassword_Success() throws Exception {
    // given
    String password = getOriginalPassword() + nextId++;
    String request = new ObjectMapper().writeValueAsString(password);

    //when
    MvcResult mvcResult = mockMvc.perform(
        patch(USER_URL + "/" + TEST_USER_CLASS_OF + "/password")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request)
    ).andReturn();

    //then
    assertEquals(200, mvcResult.getResponse().getStatus());
  }

  @Test
  @WithMockUser(username = "testUser1", roles = "USER")
  public void updatePassword_Fail_UserDoesNotMatch() throws Exception {
    // given
    String password = getOriginalPassword() + nextId++;
    String request = new ObjectMapper().writeValueAsString(password);

    //when
    MvcResult mvcResult = mockMvc.perform(
        patch(USER_URL + "/" + TEST_ADMIN_CLASS_OF + "/password")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request)
    ).andReturn();

    //then
    assertEquals(403, mvcResult.getResponse().getStatus());
  }

  @Test
  @WithMockUser(username = "notFoundUser", roles = "USER")
  public void updatePassword_Fail_UserNotFoundException() throws Exception {
    // given
    String password = getOriginalPassword() + nextId++;
    String request = new ObjectMapper().writeValueAsString(password);

    //when
    MvcResult mvcResult = mockMvc.perform(
        patch(USER_URL + "/" + "notFoundUser" + "/password")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(request)
    ).andReturn();

    //then
    assertEquals(404, mvcResult.getResponse().getStatus());
  }
}
